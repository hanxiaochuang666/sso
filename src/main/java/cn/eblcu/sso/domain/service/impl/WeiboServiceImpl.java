package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.domain.service.IWeiboService;
import cn.eblcu.sso.domain.service.UserService;
import cn.eblcu.sso.infrastructure.util.DateUtils;
import cn.eblcu.sso.infrastructure.util.StringUtils;
import cn.eblcu.sso.infrastructure.util.TokenUtils;
import cn.eblcu.sso.infrastructure.util.WeiboUtils;
import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.WeiBoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WeiboServiceImpl implements IWeiboService {

    @Value("${weibo.AppKey}")
    private String appKey;

    @Value("${weibo.AppSecret}")
    private String appSecret;

    @Value("${weibo.state}")
    private String state;

    @Value("${weibo.callBackUrl}")
    private String callBackUrl;

    @Value("${token.expires}")
    private int expires;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Override
    public String getUrl() throws DomainException {
        if (StringUtils.isEmpty(appKey) || StringUtils.isEmpty(callBackUrl) || StringUtils.isEmpty(state)) {
            log.info("参数错误");
            throw new DomainException("参数错误！");
        }
        return WeiboUtils.getRequestWeiboUrl(appKey, callBackUrl, state);
    }

    @Override
    public Map<String, Object> weiboCallBack(String code, String state1) throws Exception {

        Map<String, Object> retMap = new HashMap<>();
        if (!state1.equals(state)) {
            log.info("非法回调");
            throw new DomainException("非法回调");
        }
        //1.获取授权access_token
        Map<String, Object> objectMap = WeiboUtils.getAccessTokenStr(appKey, appSecret, code, callBackUrl);
        if (StringUtils.isEmpty(objectMap)) {
            log.info("获取授权信息失败");
            throw new DomainException("获取授权信息失败");
        }
        if (StringUtils.isEmpty(objectMap.get("access_token"))) {
            log.info("获取授权access_token失败");
            throw new DomainException("获取授权access_token失败");
        }
        if (StringUtils.isEmpty(objectMap.get("uid"))) {
            log.info("获取授权uid失败");
            throw new DomainException("获取授权uid失败");
        }
        String accessToken = objectMap.get("access_token").toString();
        String uid = objectMap.get("uid").toString();
        //2.获取用户信息
        WeiBoUser userInfo = WeiboUtils.getUserInfo(accessToken, uid);
        retMap.put("data", saveUserInfo(userInfo,uid));
        retMap.put("userInfo", userInfo);
        return retMap;
    }


    private Map<String,Object> saveUserInfo(WeiBoUser wUser,String uid) throws Exception {

        User user = new User();
        UserInfo userInfo = new UserInfo();
        Map<String, Object> retMap = new HashMap<>();
        user.setWeiboid(uid);
        Date currDate = DateUtils.now();

        List<User> users = userDao.selectUserList(user);
        if (null != users && users.size() > 0) {
            User user1 = users.get(0);
            int count = user1.getCount();
            user.setId(user1.getId());
            user.setCount(count + 1);
            user.setLastlogintime(currDate);
            userInfo.setUserid(user1.getId());
            userInfo.setNickname(wUser.getScreen_name());
            userInfo.setHeadphoto(wUser.getProfile_image_url());
            user.setUserinfo(userInfo);
            userService.editUser(user);
            int id = user1.getId();
            log.info("插入成功，返回主键id======" + id);
            String token = TokenUtils.createToken(new User(id));
            log.info("注册自动生成token============" + token);
            redisUtils.setStrByTime(token, "" + id, expires);
            retMap.put("token", token);
            retMap.put("id", id);
            retMap.put("loginName", user1.getLoginname());
        } else {
            user.setRegistertime(currDate);
            user.setCount(1);
            user.setLastlogintime(currDate);
            user.setLoginname(wUser.getScreen_name());// 默认使用注册账号作为用户名,可以通过用户信息维护接口修改
            user.setStatus(0);
            userDao.insertSelective(user);
            int id = user.getId();
            log.info("插入成功，返回主键id======" + id);
            String token = TokenUtils.createToken(new User(id));
            log.info("注册自动生成token============" + token);
            redisUtils.setStrByTime(token, "" + id, expires);
            retMap.put("token", token);
            retMap.put("id", id);
            retMap.put("loginName", user.getLoginname());
        }
        return retMap;
    }
}
