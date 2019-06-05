package cn.eblcu.sso.infrastructure.util;


import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.ui.model.UserToken;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class SupperTokenUtils {
    protected static Logger logger = Logger.getLogger(SupperTokenUtils.class);
    private SupperTokenUtils(){

    }

    /**
     * 关于整个非对称高安全token的策略：
     * 1.生成token：
     *      1.1 客户端登录时，使用自己的私钥加密json,再使用SSO平台公钥进行加密
     *      1.2 SSO收到登陆信息后,使用自己的私钥解密后，再使用预先存储到客户端公钥解密，如果解不开，则证明请求信息异常，可能是伪造信息；
     *      1.3 解密后校验用户信息后，先用私钥加密，再使用客户端的公钥信息加密用户的信息（加上随机token描述，保证每次token不一样，同时需要一个一致性算法的hash，用来做信息校验），作为token返回给客户端
     *      1.4 SSO，将该信息与用户uid在redis中建立有效时长映射；
     *      1.5 客户端收到token，每次请求带上token；
     * 2.token验证：
     *      2.1 每次SSO收到请求时，使用redis进行有效性验证；
     *
     * 3.其他平台从token中提取用户信息的方式：
     *      3.1使用自己的私钥信息解密token;
     *      3.2再使用SSO的公钥解密,得到用户信息;
     *      3.3再使用认证方法认证信息的真实性；
     */

    @Value("${register.filePath}")
    private static String keyPath;

    /**
     * 客户端公钥
     */
    public static String Other_Platform_Public_Key;

    /**
     * 客户端私钥
     */
    public static String Other_Platform_Private_Key;
    static {
        try {
            if (null != keyPath) {
                SupperTokenUtils.Other_Platform_Public_Key = FileUtils.readTxt(keyPath + "otherPublicKey.txt");
                SupperTokenUtils.Other_Platform_Private_Key = FileUtils.readTxt(keyPath + "otherPrivateKey.txt");
            } else {
                SupperTokenUtils.Other_Platform_Public_Key = FileUtils.readTxt("D:/key/otherPublicKey.txt");
                SupperTokenUtils.Other_Platform_Private_Key = FileUtils.readTxt("D:/key/otherPrivateKey.txt");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 解析加密字符串信息，获取用户信息
     * @param landStr
     * @return
     */
    public static JSONObject getUserInfoByLand(String landStr)throws Exception{
        //1.使用SSO私钥解密
        String decrypt = RsaUtils.decryptByPrivateKey(landStr, RsaUtils.PrivateKey);
        //2.使用第三方客户端公钥解密
        String decryptByPrivateKey = RsaUtils.decryptByPublicKey(decrypt, SupperTokenUtils.Other_Platform_Public_Key);
        //3.解析为JSONObject
        JSONObject parse1 = JSONObject.parseObject(decryptByPrivateKey);
        String loginname = parse1.get("loginname").toString();
        String password = parse1.get("password").toString();
        String authenStr1 = parse1.get("authenStr").toString();
        User user = new User();
        user.setLoginname(loginname);
        user.setPassword(password);
        //4.验证信息
        String byUser = TokenUtils.hashStrByUser(user);
        if(!authenStr1.equals(byUser)){
            logger.info("非法的验证信息");
            return null;
        }
        return parse1;
    }

    /**
     * 获取token
     * @param user
     * @return
     */
    public static String getSupperToken(User user)throws  Exception{
        UserToken userToken=new UserToken();

        //1.先获取动态token
        String token = TokenUtils.createToken(user);
        userToken.setAuthenStr(token);
        userToken.setId(user.getId());
        //2.添加验证信息
        String tokenCheck = TokenUtils.hashStrByUserToken(userToken);
        System.out.println("tokenCheckStr:"+tokenCheck);
        userToken.setTokenCheckStr(tokenCheck);
        //3.使用SSO私钥签名
        String jsonString = JSONObject.toJSONString(userToken);
        String encryptByprivateKey = RsaUtils.encryptByprivateKey(jsonString, RsaUtils.PrivateKey);
        //4.使用客户端公钥加密
        String encryptByPublicKey = RsaUtils.encryptByPublicKey(encryptByprivateKey, SupperTokenUtils.Other_Platform_Public_Key);
        return encryptByPublicKey;
    }

    /**
     * 从token中获取用户信息
     * @param token
     * @return
     */
    public static UserToken getUserByToken(String token)throws Exception{
        //1.使用自己的私钥解密
        String decryptByPrivateKey = RsaUtils.decryptByPrivateKey(token, SupperTokenUtils.Other_Platform_Private_Key);
        //2.使用SSO的公钥解密
        String decryptByPublicKey = RsaUtils.decryptByPublicKey(decryptByPrivateKey, RsaUtils.PublicKey);
        //3.str to Object
        UserToken user = JSON.parseObject(decryptByPublicKey, UserToken.class);
        //4.认证信息
        String tokenCheck = TokenUtils.hashStrByUserToken(user);
        if(!user.getTokenCheckStr().equals(tokenCheck)){
            logger.info("非法的信息");
            return null;
        }
        return user;
    }
}
