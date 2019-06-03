package cn.eblcu.sso.infrastructure.util;

import java.util.Random;


public class IdentifyingCodeUtils {
    private IdentifyingCodeUtils(){
    }
    /**
     * @Author 焦冬冬
     * @Description
     * @Date 11:34 2019/5/29
     * @Param length  随机码的长度
     * @return
     **/
    public static String createCode(int length){
        Random random = new Random();
        StringBuilder res=new StringBuilder();
        for (int i=0;i<length;i++){
            int key=random.nextInt(5);
            switch (key){
                case 0:
                    res.append(random.nextInt(10));
                    break;
                case 1:
                    res.append((char)(random.nextInt(13)+65));
                    break;
                case 2:
                    res.append((char)(random.nextInt(13)+65+13));
                    break;
                case 3:
                    res.append((char)(random.nextInt(13)+97));
                    break;
                case 4:
                    res.append((char)(random.nextInt(13)+97+13));
                    break;
            }
        }
        return  res.toString();
    }
}
