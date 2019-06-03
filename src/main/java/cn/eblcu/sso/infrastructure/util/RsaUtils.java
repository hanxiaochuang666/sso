package cn.eblcu.sso.infrastructure.util;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaUtils {
    private RsaUtils(){

    }
    public  static String PublicKey;
    public  static String PrivateKey;
    /**
     * 获取秘钥对
     * @param length
     * @return
     * @throws Exception
     */
    public static Map<String,String> getKeyPair(int length)throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(length);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        // 得到私钥字符串
        String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
        Map<String,String> map=new HashMap<>();
        PublicKey=publicKeyString;
        PrivateKey=privateKeyString;
        map.put("publicKey",publicKeyString);
        map.put("privateKey",privateKeyString);
        return map;
    }

    /**
     * 根据公钥字符串获取公钥
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    private static RSAPublicKey getPublicKeyByStr(String publicKeyStr)throws Exception{
        byte[] buffer = Base64.decodeBase64(publicKeyStr.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 根据私钥字符串获取私钥
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    private static RSAPrivateKey getPrivateKeyByStr(String privateKeyStr)throws Exception{
        byte[] buffer = Base64.decodeBase64(privateKeyStr.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * 使用公钥进行加密
     * @param content
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String  publicKeyStr)throws Exception{
        RSAPublicKey publicKey = getPublicKeyByStr(publicKeyStr);
        byte[] bytes = content.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(bytes);
        return new String(Base64.encodeBase64String(output));
    }

    /**
     * 使用私钥进行解密
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String content,String privateKeyStr)throws  Exception{
        RSAPrivateKey privateKey = getPrivateKeyByStr(privateKeyStr);
        byte[] bytes = Base64.decodeBase64(content.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] output = cipher.doFinal(bytes);
        return new String(output);
    }
}
