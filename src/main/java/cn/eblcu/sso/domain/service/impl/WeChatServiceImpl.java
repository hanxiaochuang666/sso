package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.domain.service.IWeChatService;
import cn.eblcu.sso.infrastructure.util.MapAndObjectUtils;
import cn.eblcu.sso.infrastructure.util.StringUtils;
import cn.eblcu.sso.infrastructure.util.WeixinUtils;
import cn.eblcu.sso.ui.model.WeChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
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
    @Override
    public WeChatUser landCallBack(String code, String state1) throws DomainException{
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
        return weChatUser;
    }

    @Override
    public String getUrl() {
        return WeixinUtils.toRequestCodeUrl(appid, callBackUrl,state);
    }
}
