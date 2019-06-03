package cn.eblcu.sso;

import cn.eblcu.sso.infrastructure.util.RsaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RsaUtilsTest {

    @Test
    public void test1()throws Exception{
        Map<String, String> keyPair = RsaUtils.getKeyPair(512);
        System.out.println("公钥"+keyPair.get("publicKey"));
        System.out.println("私钥"+keyPair.get("privateKey"));
        String encrypt = RsaUtils.encrypt("w爹职业dasdasdsa", RsaUtils.PublicKey);
        System.out.println("加密后:"+encrypt);
        String decrypt = RsaUtils.decrypt(encrypt, RsaUtils.PrivateKey);
        System.out.println("解密后:"+decrypt);
    }
}
