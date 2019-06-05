package cn.eblcu.sso;

import cn.eblcu.sso.infrastructure.util.RsaUtils;
import cn.eblcu.sso.infrastructure.util.SupperTokenUtils;
import cn.eblcu.sso.infrastructure.util.TokenUtils;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.ui.model.UserToken;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupperTokenUtilsTest {
    @Test
    public void test1()throws Exception{
        //1.制造客户端登录信息
        JSONObject JObject=new JSONObject();
        JObject.put("loginname","jdd287061");
        JObject.put("password","jdd287061");
        JObject.put("landType","3");
        User user = new User();
        user.setLoginname("jdd287061");
        user.setPassword("jdd287061");
        String strByUser = TokenUtils.hashStrByUser(user);
        JObject.put("authenStr",strByUser);
        String jsonString = JObject.toJSONString();
        String encryptByprivateKey = RsaUtils.encryptByprivateKey(jsonString, SupperTokenUtils.Other_Platform_Private_Key);
        String encryptByPublicKey = RsaUtils.encryptByPublicKey(encryptByprivateKey, RsaUtils.PublicKey);
        //2.登陆解析信息
        JSONObject userJsonObject = SupperTokenUtils.getUserInfoByLand(encryptByPublicKey);

        System.out.println("user1 log"+userJsonObject.getString("loginname"));
        System.out.println("user1 passward"+userJsonObject.getString("password"));
        System.out.println("user1 authenStr"+userJsonObject.getString("authenStr"));
        //3.生成token
        String supperToken = SupperTokenUtils.getSupperToken(user);
        System.out.println("token:"+supperToken);
        //4.通过token解析user
        UserToken userByToken = SupperTokenUtils.getUserByToken(supperToken);
        System.out.println("user2 log"+userByToken.getId());

    }
}
