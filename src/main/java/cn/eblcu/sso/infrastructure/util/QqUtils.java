package cn.eblcu.sso.infrastructure.util;


import cn.eblcu.sso.ui.model.QqUser;
import org.apache.http.HttpResponse;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @ClassName QqUtils
 * @Author 焦冬冬
 * @Date 2019/5/14 15:12
 * 腾讯开放平台登陆四步骤
 *      1.打开二维码扫描页面
 *      2.在回调接口中，根据code及appid、appkey来获取access_token
 *      3.根据2步骤返回的refresh_token和appid、appkey来刷新access_token
 *      4.根据access_token来获取openid openid是此网站上唯一对应用户身份的标识，网站可将此ID进行存储便于用户下次登录时辨识其身份，或将其与用户在网站上的原有账号进行绑定。
 *      5.根据access_token、openid、appid来获取用户信息
 **/
public class QqUtils {
    private QqUtils(){}
    //请求二维码图片
    private static String requestQqUrl=" https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s";

    //获取Access Token的URL
    private static String getAccessTokenUrl="https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    //刷新access_token的URL
    private static String refreshTokenuRL="https://graph.qq.com/oauth2.0/token?grant_type=refresh_token&client_id=%s&client_secret=%s&refresh_token=%s";

    //获取openId的URL
    private static String getOpenIdUrl="https://graph.qq.com/oauth2.0/me?access_token=%s";

    //获取用户信息的url
    private static String getUserInfoUrl="https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";
    
    /**
     * @Author 焦冬冬
     * @Description 
     * @Date 17:06 2019/5/14
     * @Param
     * clientId  申请的appid
     * redirectUri 授权成功后的回调
     * state client端的状态值,授权成功后会回传该值，可以用来防止第三方攻击
     * @return 
     **/
    public static String getRequestQqUrl(String clientId,String redirectUri,String state){
        redirectUri = URLEncoder.encode(redirectUri);
        return String.format(requestQqUrl,clientId,redirectUri,state);
    }
    
    /**
     * @Author 焦冬冬
     * @Description 
     * @Date 17:23 2019/5/14
     * @Param
     * * clientId  申请的appid
     * * clientSecret 申请的appkey
     * * redirectUri 授权成功后的回调
     * * code 回调是返回的code，此code会在10分钟内过期。
     * @return 
     **/
    public static Map<String,Object> getaccessTokenStr(String clientId, String clientSecret, String code, String redirectUri)throws Exception{
        redirectUri = URLEncoder.encode(redirectUri);
        String format = String.format(getAccessTokenUrl, clientId, clientSecret, code, redirectUri);
        //获取get请求
        HttpResponse httpResponse = HttpReqUtil.getObjectReq( format , null ) ;
        //解析get请求
        Map<String,Object> responsemap = HttpReqUtil.parseUrlHttpResponse( httpResponse ) ;
        Object expires_in = responsemap.get("expires_in") ;
        if( !StringUtils.isEmpty(expires_in)  ){
            if(  Integer.valueOf(expires_in.toString()).intValue() < 1000 ){
                // 刷新access_token
                refreshToken( clientId , clientSecret, responsemap.get("refresh_token").toString() ) ;
            }
        }
        return responsemap;
    }
    
    /**
     * @Author 焦冬冬
     * @Description 刷新access_token Access_Token的有效期默认是3个月，过期后需要用户重新授权才能获得新的Access_Token。
     * @Date 17:26 2019/5/14
     * @Param 
     * @return 
     **/
    private static void refreshToken(String clientId, String clientSecret, String refreshToken)throws Exception{
        String format = String.format(refreshTokenuRL, clientId, clientSecret, refreshToken);
        HttpReqUtil.getObjectReq( format , null ) ;
    }

    /**
     * @Author 焦冬冬
     * @Description 根据accessToken来获取openId
     * @Date 17:42 2019/5/14
     * @Param
     * @return
     **/
    public static String getOpenId(String accessToken)throws Exception{
        String format = String.format(getOpenIdUrl, accessToken);
        HttpResponse httpResponse = HttpReqUtil.getObjectReq(format, null);
        Map<String,Object> responsemap = HttpReqUtil.parseQQHttpResponse( httpResponse ) ;
        Object openid = responsemap.get("openid");
        if(StringUtils.isEmpty(openid))
            return null;
        return  openid.toString();
    }
    
    /**
     * @Author 焦冬冬
     * @Description 
     * @Date 17:52 2019/5/14
     * @Param 
     * @return 
     **/
    public static QqUser getUserInfo(String access_token, String appid, String openid)throws Exception{
        access_token = URLEncoder.encode(access_token);
        appid = URLEncoder.encode(appid);
        openid = URLEncoder.encode(openid);
        String format = String.format(getUserInfoUrl, access_token, appid, openid);
        HttpResponse httpResponse = HttpReqUtil.getObjectReq( format , null ) ;
        Map<String,Object> responsemap = HttpReqUtil.parseHttpResponse( httpResponse ) ;
        return (QqUser)MapAndObjectUtils.MapToObject(responsemap, QqUser.class);
    }
}
