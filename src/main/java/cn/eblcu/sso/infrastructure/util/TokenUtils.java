package cn.eblcu.sso.infrastructure.util;


import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.ui.model.UserToken;

import java.security.MessageDigest;
import java.util.Random;

public class TokenUtils {

    private TokenUtils(){

    }
    /**
     *
     * @param user  用户信息
     * @return
     */
    public static String createToken(User user){
        String userStr = user.toString();
        StringBuilder stringBuilder = new StringBuilder();
        //1.使用SHA1加密
            //使用随机盐值加权字符串
            cutStr(stringBuilder,userStr);
            String res=stringBuilder.toString();
            //加密
            String result = "";
            try {
                MessageDigest hash=MessageDigest.getInstance("SHA1");
                byte[] bytes = hash.digest(res.getBytes("UTF-8"));

                for (byte b : bytes) {
                    String temp = Integer.toHexString(b & 0xff);
                    if (temp.length() == 1) {
                        temp = "0" + temp;
                    }
                    result += temp;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return result;
    }

    //加盐算法(加盐的算法是为了避免 即使是同一条用户信息，每次执行的结果也不一样，避免被破解)
    private static void cutStr(StringBuilder stringBuilder,String str){
        if(str.length()<5){
            stringBuilder.append(str);
            return;
        }else{
            int random = new Random().nextInt(str.length());
            stringBuilder.append(str.substring(0,random));
            int nextInt = new Random().nextInt(99999999);
            stringBuilder.append(nextInt);
            cutStr(stringBuilder,str.substring(random));
        }
    }

    /**
     * 特有的hash算法，确认登陆客户端身份的真实性
     * 将user分为5段生成hash值；
     * 将5段hash值再次做hash运算
     * SHA1最为安全，目前为止还没有找到一次碰撞案例
     */
    public static String hashStrByUser(User user)throws Exception{
        if(StringUtils.isEmpty(user.getId()))
            user.setId(0);
        if(StringUtils.isEmpty(user.getLoginname()))
            user.setLoginname("null");
        if(StringUtils.isEmpty(user.getPassword()))
            user.setPassword("null");
        if(StringUtils.isEmpty(user.getEmail()))
            user.setEmail("www.beiyuda");
        if(StringUtils.isEmpty(user.getMobile()))
            user.setMobile(new Long(18513252453L));
        if(StringUtils.isEmpty(user.getStatus()))
            user.setStatus(5);
        if(StringUtils.isEmpty(user.getQqopenid()))
            user.setQqopenid("ndadadd");
        if(StringUtils.isEmpty(user.getWechatid()))
            user.setWechatid("davYu6");
        if(StringUtils.isEmpty(user.getWeiboid()))
            user.setWeiboid("7yvm5P2");
        if(StringUtils.isEmpty(user.getLastloginip()))
            user.setLastloginip("1.1.111.1");
        String result="";
        String str1=user.getId()+user.getLoginname();
        String str2=user.getPassword()+user.getEmail();
        String str3=user.getMobile()+user.getStatus()+"";
        String str4=user.getQqopenid()+user.getWechatid();
        String str5=user.getWeiboid()+user.getLastloginip();
        MessageDigest hash=MessageDigest.getInstance("SHA1");
        String res1 = sha1Hash(hash, str1);
        String res2 = sha1Hash(hash, str2);
        String res3 = sha1Hash(hash, str3);
        String res4 = sha1Hash(hash, str4);
        String res5 = sha1Hash(hash, str5);
        result= sha1Hash(hash,res1 + res2 + res3 + res4 + res5);
        return result;
    }

    private static  String sha1Hash(MessageDigest hash,String in)throws Exception{
        byte[] bytes = hash.digest(in.getBytes("UTF-8"));
        String result="";
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result += temp;
        }
        return result;
    }

    /**
     * 校验userToken信息的真实性
     * @param userToken
     * @return
     */
    public static String hashStrByUserToken(UserToken userToken)throws Exception{
        if(StringUtils.isEmpty(userToken.getAuthenStr()))
            return null;
        if(StringUtils.isEmpty(userToken.getId()))
            return null;
        String sec="xljR3NAwvsgpeIoN3/7c2GOcaqW0j4fDVIwYaYz67/GXBD18jB9qwu5no/aSvy2AwJkMQHPPF/zIgi0beJ6uFix0BvngOwmn/JppRKmVBaK57ncrkXsTySSmhi6p4GgEp5/0RlhrsLzElzjicOSDQxVAcoxruWe0wPOnh3cbCawDkGp/mD6aDamz9D3";
        String authenStr = userToken.getAuthenStr();
        int length = sec.length();
        length=length/8;
        StringBuilder resBuild=new StringBuilder();
        for(int i=0;i<8;i++){
            if(i==3)
                resBuild.append(userToken.getId());
            resBuild.append(sec.substring(i*length,(i+1)*length));
            resBuild.append(authenStr.substring(i*5,(i+1)*5));
        }
        String toString = resBuild.toString();
        MessageDigest hash=MessageDigest.getInstance("SHA1");
        return sha1Hash(hash,toString);
    }
}
