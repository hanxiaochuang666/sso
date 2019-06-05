package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.domain.service.IWeChatService;
import cn.eblcu.sso.domain.service.UserService;
import cn.eblcu.sso.infrastructure.util.*;
import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.QqUser;
import cn.eblcu.sso.ui.model.WeChatUser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WeChatServiceImpl implements IWeChatService {
    Logger logger = LoggerFactory.getLogger(WeChatServiceImpl.class);

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.appSecret}")
    private String appSecret;

    @Value("${weixin.state}")
    private String state;

    @Value("${weixin.callBackUrl}")
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
    public Map<String, Object> landCallBack(String code, String state1) throws Exception{

        Map<String, Object> retMap = new HashMap<>();
        if(!state1.equals(state)){
            logger.info("非法的回调");
            throw new DomainException("非法的回调");
        }
        Map<String, Object> accessTokenReq=null;
        //1.根据code获取accessToken;
        try {
            accessTokenReq= WeixinUtils.accessTokenReq(appid, appSecret, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!StringUtils.isEmpty(accessTokenReq.get("errcode"))){
            logger.info("errCode"+accessTokenReq.get("errMsg").toString());
            throw new DomainException(accessTokenReq.get("errMsg").toString());
        }
        String accessToken=accessTokenReq.get("access_token").toString();
        String openid=accessTokenReq.get("openid").toString();
        //2.根据根据code获取accessToken获取用户信息
        Map<String, Object> userInfoReqMap=null;
        try {
            userInfoReqMap = WeixinUtils.userInfoReq(accessToken, openid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!StringUtils.isEmpty(userInfoReqMap.get("errCode"))){
            logger.info("errCode"+accessTokenReq.get("errMsg").toString());
            throw new DomainException(accessTokenReq.get("errMsg").toString());
        }
        WeChatUser weChatUser =null;
        //3.解析数据
        try {
            weChatUser = (WeChatUser)MapAndObjectUtils.MapToObject(userInfoReqMap, WeChatUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        retMap.put("data", saveUserInfo(weChatUser,openid));
        retMap.put("userInfo", weChatUser);
        return retMap;
    }

    @Override
    public String getUrl() {
        return WeixinUtils.toRequestCodeUrl(appid, callBackUrl,state);
    }


    private Map<String,Object> saveUserInfo(WeChatUser weChatUser, String openId) throws Exception {

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
            userInfo.setNickname(weChatUser.getNickname());
            userInfo.setHeadphoto(weChatUser.getHeadimgurl());
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
            user.setLoginname(weChatUser.getNickname());// 默认使用注册账号作为用户名,可以通过用户信息维护接口修改
            user.setStatus(0);
            userDao.insertSelective(user);
            int id = user.getId();
            log.info("插入返回主键id======" + id);
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
