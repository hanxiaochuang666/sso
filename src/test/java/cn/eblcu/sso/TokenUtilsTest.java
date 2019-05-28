package cn.eblcu.sso;

import cn.eblcu.sso.domain.service.RedisUtils;
import cn.eblcu.sso.infrastructure.util.TokenUtils;
import cn.eblcu.sso.persistence.entity.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName TokenUtilsTest
 * @Author 焦冬冬
 * @Date 2019/5/27 13:45
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenUtilsTest {
    @Autowired
    private RedisUtils redisUtils;
    @Test
    public void createTokenTest(){
        User user = new User();
        user.setLoginname("jdd287061");
        user.setPassword("4545415");
        String token = TokenUtils.createToken(user);
        System.out.println(token);
    }
    @Test
    public void redisTest(){
        String myKey = redisUtils.getByKey("myKey");
        System.out.println(myKey);
        redisUtils.setStr("jdd","11111");
        redisUtils.refreshTime("jdd",15);
    }

    @Test
    public void testCreateRoken(){
        User user = new User();
        user.setId(15);
        user.setLoginname("jdd287061");
        user.setPassword("4545415");
        String token = TokenUtils.createToken(user);
        redisUtils.setStrByTime("myKey",user.getId()+"",3000);
        System.out.println(token);
    }

    @Test
    public void testValidToken(){
        boolean isexists = redisUtils.isexists("15");
        if(isexists) {
            System.out.println("存在");
            System.out.println(redisUtils.getByKey("15"));
        }
        else System.out.println("不存在");
    }
}