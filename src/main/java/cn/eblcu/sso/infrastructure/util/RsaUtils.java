package cn.eblcu.sso.infrastructure.util;


import com.google.common.primitives.Bytes;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class RsaUtils {
    private RsaUtils(){

    }

    /**
     * 公钥
     */
    public  static String PublicKey;
    /**
     * 私钥
     */
    public  static String PrivateKey;

    /**
     * 最大加密长度 单位:字节
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 最大解密长度 单位：字节
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 秘钥长度
     */
    private static final int KEY_SIZE=1024;

    @Value("${register.filePath}")
    private static String keyPath;

    /**
     * 启动时加载秘钥
     */
    static {
        try {
            createKeyPairFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(null!=keyPath){
                RsaUtils.PublicKey = FileUtils.readTxt(keyPath+"publicKey.txt");
                RsaUtils.PrivateKey = FileUtils.readTxt(keyPath+"privateKey.txt");
            }else {
                RsaUtils.PublicKey = FileUtils.readTxt("D:/key/publicKey.txt");
                RsaUtils.PrivateKey = FileUtils.readTxt("D:/key/privateKey.txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @Author 焦冬冬
     * @Description 生成密钥对文件
     * @Date 14:00 2019/6/3
     * @Param
     * @return
     **/
    public static void createKeyPairFile()throws Exception{
        Map<String, String> keyPair = getKeyPair(KEY_SIZE);
        if(StringUtils.isEmpty(keyPath))
            keyPath="D:/key/";
        String privateKeyFileStr=keyPath+"privateKey.txt";
        File privateKeyFile = new File(privateKeyFileStr);
        int validFlag=0;
        if(privateKeyFile.exists()) {
            System.out.println("私钥文件已存在:"+privateKeyFileStr);
            validFlag++;
        }
        String publicKeyFileStr=keyPath+"publicKey.txt";
        File publicKeyFile = new File(publicKeyFileStr);
        if(publicKeyFile.exists()) {
            System.out.println("公钥文件已存在:"+publicKeyFileStr);
            validFlag++;
        }
        //两个都存在时,返回
        if(2==validFlag)
            return;
        privateKeyFile.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(privateKeyFile));
        out.write(keyPair.get("privateKey"));
        out.flush();
        out.close();
        out=new BufferedWriter(new FileWriter(publicKeyFile));
        out.write(keyPair.get("publicKey"));
        out.flush();
        out.close();
    }

    /**
     * 获取秘钥对私有方法
     * @param length
     * @return
     * @throws Exception
     */
    private static Map<String,String> getKeyPair(int length)throws Exception{
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
    public static String encryptByPublicKey(String content, String  publicKeyStr)throws Exception{
        RSAPublicKey publicKey = getPublicKeyByStr(publicKeyStr);
        byte[] bytes = content.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] output =partOpt(cipher,Cipher.ENCRYPT_MODE,bytes);
        return new String(Base64.encodeBase64String(output));
    }

    /**
     * 使用私钥进行解密
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String content,String privateKeyStr)throws  Exception{
        RSAPrivateKey privateKey = getPrivateKeyByStr(privateKeyStr);
        byte[] bytes = Base64.decodeBase64(content.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] output =partOpt(cipher,Cipher.DECRYPT_MODE,bytes);
        return new String(output);
    }

    /**
     * 使用私钥加密
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String encryptByprivateKey(String content,String privateKeyStr)throws Exception{
        RSAPrivateKey privateKey = getPrivateKeyByStr(privateKeyStr);
        byte[] bytes = content.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] output =partOpt(cipher,Cipher.ENCRYPT_MODE,bytes);
        return new String(Base64.encodeBase64String(output));
    }

    /**
     * 使用公钥解密
     * @param content
     * @param publicKeyStr
     * @return
     */
    public  static String decryptByPublicKey(String content,String publicKeyStr)throws Exception{
        RSAPublicKey publicKey = getPublicKeyByStr(publicKeyStr);
        byte[] bytes = Base64.decodeBase64(content.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] output =partOpt(cipher,Cipher.DECRYPT_MODE,bytes);
        return new String(output);
    }

    /**
     * 分段处理加密解密
     * @param cipher
     * @param mode
     * @param bytes
     * @return
     * @throws Exception
     */
    private static byte[] partOpt(Cipher cipher,final int mode,byte[] bytes)throws Exception{
        int length = bytes.length;
        int offset=0;
        int criticalVal=0;
        if(mode==Cipher.DECRYPT_MODE)
            criticalVal=MAX_DECRYPT_BLOCK;
        else if(mode==Cipher.ENCRYPT_MODE)
            criticalVal=MAX_ENCRYPT_BLOCK;
        else
            return null;
        List<Byte> ByteObjLst=new ArrayList<>();
        int flagVal=length-offset;
        while (flagVal>0){
            if(flagVal>criticalVal) {
                byte[] bytes1 = cipher.doFinal(bytes, offset, criticalVal);
                ByteObjLst.addAll(Bytes.asList(bytes1));
            }else{
                byte[] bytes1 = cipher.doFinal(bytes, offset, length - offset);
                ByteObjLst.addAll(Bytes.asList(bytes1));
            }
            offset+=criticalVal;
            flagVal=length-offset;
        }
        return Bytes.toArray(ByteObjLst);
    }
}
