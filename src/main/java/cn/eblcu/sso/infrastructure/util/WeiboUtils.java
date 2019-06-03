package cn.eblcu.sso.infrastructure.util;

import cn.eblcu.sso.ui.model.WeiBoUser;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;

import java.util.Map;

public class WeiboUtils {

    private WeiboUtils(){}
    protected static Logger logger = Logger.getLogger( WeiboUtils.class );
    /**
     * 获取跳转url
     */
    private static String requestWeiboUrl="https://api.weibo.com/oauth2/authorize?client_id=%s&redirect_uri=%s&state=%s";
    /**
     * 获取access_token 的URL
     */
    private static String getAccessTokenUrl="https://api.weibo.com/oauth2/access_token?client_id=%s&client_secret=%s&grant_type=authorization_code&code=%s&redirect_uri=%s";

    /**
     * 获取用户信息的URL
     */
    private static String getUserInfoUrl="https://api.weibo.com/2/users/show.json?access_token=%s&uid=%s";
    /**
     * @Author 焦冬冬
     * @Description 获取跳转回调
     * @Date 10:16 2019/5/16
     * @Param
     * clientId     申请的appKey
     * redirectUri  授权成功后的回调
     * state client 端的状态值,授权成功后会回传该值，可以用来防止第三方攻击
     * @return
     **/
    public  static  String getRequestWeiboUrl(String AppKey,String redirectUri,String state){
        return String.format(requestWeiboUrl,AppKey,redirectUri,state);
    }
    
    /**
     * @Author 焦冬冬
     * @Description 
     * @Date 10:30 2019/5/16
     * @Param
     * client_id        申请应用时分配的AppKey。
     * client_secret	申请应用时分配的AppSecret。
     * grant_type       请求的类型，填写authorization_code
     * code	true        调用authorize获得的code值。
     * redirect_uri     回调地址，需需与注册应用里的回调地址一
     * @return
     * access_token     用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该用该票据和自己应用内的用户建立唯一影射关系，来识别登录状态，不能使用本返回值里的UID字段来做登录识别。
     * expires_in       access_token的生命周期，单位是秒数。 一般是30天
     * uid	            授权用户的UID，本字段只是为了方便开发者，减少一次user/show接口调用而返回的，第三方应用不能用此字段作为用户登录状态的识别，只有access_token才是用户授权的唯一票据。
     **/
    public  static Map<String,Object> getAccessTokenStr(String AppKey,String AppSecret,String code,String redirectUri)throws Exception{
        //JSONObject temp=new JSONObject();
        String format = String.format(getAccessTokenUrl, AppKey, AppSecret, code, redirectUri);
        //获取post请求
        HttpResponse httpResponse = HttpReqUtil.postObjectReq( format , null ) ;
        //解析post请求
        Map<String,Object> responsemap = HttpReqUtil.parseHttpResponse( httpResponse ) ;
        return responsemap;
    }

    /**
     * @Author 焦冬冬
     * @Description 获取用户信息
     * @Date  2019/5/16
     * @Param
     * @return
     **/
    public static WeiBoUser getUserInfo(String accessToken, String uid)throws Exception{
        String format = String.format(getUserInfoUrl, accessToken, uid);
        //获取get请求
        HttpResponse httpResponse = HttpReqUtil.getObjectReq( format , null ) ;
        Map<String,Object> responsemap = HttpReqUtil.parseHttpResponse( httpResponse ) ;
        return (WeiBoUser)MapAndObjectUtils.MapToObject(responsemap,WeiBoUser.class);
    }


}
