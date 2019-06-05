package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.domain.service.IQqService;
import cn.eblcu.sso.domain.service.UserService;
import cn.eblcu.sso.infrastructure.util.DateUtils;
import cn.eblcu.sso.infrastructure.util.QqUtils;
import cn.eblcu.sso.infrastructure.util.StringUtils;
import cn.eblcu.sso.infrastructure.util.TokenUtils;
import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.QqUser;
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
public class QqServiceImpl implements IQqService {

    @Value("${qq.appid}")
    private String appid;

    @Value("${qq.appSecret}")
    private String appSecret;

    @Value("${qq.callBackUrl}")
    private String callBackUrl;

    @Value("${qq.state}")
    private String state;

    @Value("${weixin.state}")
    private String weChatState;

    @Value("${weibo.state}")
    private String weibostate;

    @Value("${token.expires}")
    private int expires;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Override
    public String getUrl() {
        return QqUtils.getRequestQqUrl(appid, callBackUrl, state);
    }

    @Override
    public Map<String, Object> qqCallBack(String code, String state) throws Exception {

        Map<String, Object> retMap = new HashMap<>();
        //1.判断state是否正确
        if (!state.equals(this.state)) {
            log.info("非法的state：" + state);
            throw new DomainException("");
        }
        //2.回调获取用户信息
        //2.1通过Authorization Code获取Access Token
        Map<String, Object> stringObjectMap = QqUtils.getaccessTokenStr(appid, appSecret, code, callBackUrl);
        if (null == stringObjectMap) {
            log.info("获取Access Token失败");
            throw new DomainException("");
        }
        //2.2根据access_token获取openId
        Object access_token = stringObjectMap.get("access_token");
        if (StringUtils.isEmpty(access_token)) {
            log.info("获取Access Token失败");
            throw new DomainException("");
        }
        String s = access_token.toString();
        String openId = QqUtils.getOpenId(s);
        if (StringUtils.isEmpty(openId)) {
            log.info("获取openId失败");
            throw new DomainException("");
        }
        //3.根据openId获取用户信息
        QqUser userInfo = QqUtils.getUserInfo(s, appid, openId);
        retMap.put("data", saveUserInfo(userInfo,openId));
        retMap.put("userInfo", userInfo);
        return retMap;
    }

    @Override
    public Map<String, String> getValidState() {
        Map<String, String> initMap = new HashMap<>();
        initMap.put("weChatState", weChatState);
        initMap.put("qqState", state);
        initMap.put("weiboState", weibostate);
        return initMap;
    }

    private Map<String,Object> saveUserInfo(QqUser qqUser,String openId) throws Exception {

        User user = new User();
        UserInfo userInfo = new UserInfo();
        Map<String, Object> retMap = new HashMap<>();
        user.setWeiboid(openId);
        Date currDate = DateUtils.now();

        List<User> users = userDao.selectUserList(user);
        if (null != users && users.size() > 0) {
            User user1 = users.get(0);
            int count = user1.getCount();
            user.setId(user1.getId());
            user.setCount(count + 1);
            user.setLastlogintime(currDate);
            userInfo.setUserid(user1.getId());
            userInfo.setNickname(qqUser.getNickname());
            userInfo.setHeadphoto(qqUser.getFigureurl_qq_1());
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
            user.setLoginname(qqUser.getNickname());// 默认使用注册账号作为用户名,可以通过用户信息维护接口修改
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
