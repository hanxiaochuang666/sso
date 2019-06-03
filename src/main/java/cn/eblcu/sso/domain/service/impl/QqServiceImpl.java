package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.domain.service.IQqService;
import cn.eblcu.sso.infrastructure.util.QqUtils;
import cn.eblcu.sso.infrastructure.util.StringUtils;
import cn.eblcu.sso.ui.model.QqUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class QqServiceImpl implements IQqService {
    private Logger logger = LoggerFactory.getLogger(QqServiceImpl.class);

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

    @Override
    public String getUrl() {
        return QqUtils.getRequestQqUrl(appid, callBackUrl, state);
    }

    @Override
    public QqUser qqCallBack(String code, String state) throws Exception {
        //1.判断state是否正确
        if (!state.equals(this.state)) {
            logger.info("非法的state：" + state);
            throw new DomainException("");
        }
        //2.回调获取用户信息
        //2.1通过Authorization Code获取Access Token
        Map<String, Object> stringObjectMap = QqUtils.getaccessTokenStr(appid, appSecret, code, callBackUrl);
        if (null == stringObjectMap) {
            logger.info("获取Access Token失败");
            throw new DomainException("");
        }
        //2.2根据access_token获取openId
        Object access_token = stringObjectMap.get("access_token");
        if (StringUtils.isEmpty(access_token)) {
            logger.info("获取Access Token失败");
            throw new DomainException("");
        }
        String s = access_token.toString();
        String openId = QqUtils.getOpenId(s);
        if (StringUtils.isEmpty(openId)) {
            logger.info("获取openId失败");
            throw new DomainException("");
        }
        //3.根据openId获取用户信息
        QqUser userInfo = QqUtils.getUserInfo(s, appid, openId);
        return userInfo;
    }

    @Override
    public Map<String, String> getValidState() {
        Map<String, String> initMap = new HashMap<>();
        initMap.put("weChatState", weChatState);
        initMap.put("qqState", state);
        initMap.put("weiboState", weibostate);
        return initMap;
    }
}
