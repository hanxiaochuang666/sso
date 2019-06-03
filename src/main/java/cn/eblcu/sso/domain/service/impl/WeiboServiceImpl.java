package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.domain.service.IWeiboService;
import cn.eblcu.sso.infrastructure.util.StringUtils;
import cn.eblcu.sso.infrastructure.util.WeiboUtils;
import cn.eblcu.sso.ui.model.WeiBoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeiboServiceImpl implements IWeiboService {
    Logger logger = LoggerFactory.getLogger(WeiboServiceImpl.class);

    @Value("${weibo.AppKey}")
    private String appKey;

    @Value("${weibo.AppSecret}")
    private String appSecret;

    @Value("${weibo.state}")
    private String state;

    @Value("${weibo.callBackUrl}")
    private String callBackUrl;
    @Override
    public String getUrl() throws DomainException {
        if(StringUtils.isEmpty(appKey)||StringUtils.isEmpty(callBackUrl)||StringUtils.isEmpty(state)){
            logger.info("参数错误");
            throw new DomainException("参数错误！");
        }
        return WeiboUtils.getRequestWeiboUrl(appKey,callBackUrl,state);
    }

    @Override
    public WeiBoUser weiboCallBack(String code, String state1) throws Exception {
        if(!state1.equals(state)){
            logger.info("非法回调");
            throw new DomainException("非法回调");
        }
        //1.获取授权access_token
        Map<String, Object> objectMap = WeiboUtils.getAccessTokenStr(appKey, appSecret, code, callBackUrl);
        if(StringUtils.isEmpty(objectMap)){
            logger.info("获取授权信息失败");
            throw new DomainException("获取授权信息失败");
        }
        if(StringUtils.isEmpty(objectMap.get("access_token"))){
            logger.info("获取授权access_token失败");
            throw new DomainException("获取授权access_token失败");
        }
        if(StringUtils.isEmpty(objectMap.get("uid"))){
            logger.info("获取授权uid失败");
            throw new DomainException("获取授权uid失败");
        }
        String accessToken = objectMap.get("access_token").toString();
        String uid = objectMap.get("uid").toString();
        //2.获取用户信息
        WeiBoUser userInfo = WeiboUtils.getUserInfo(accessToken, uid);
        return userInfo;
    }
}
