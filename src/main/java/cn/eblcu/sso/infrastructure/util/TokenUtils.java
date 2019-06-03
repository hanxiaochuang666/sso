package cn.eblcu.sso.infrastructure.util;


import cn.eblcu.sso.persistence.entity.dto.User;

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
            System.out.println(stringBuilder.toString());
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
        System.out.println(result);
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

}
