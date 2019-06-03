package cn.eblcu.sso;


import cn.eblcu.sso.infrastructure.util.IdentifyingCodeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdentifyingCodeUtilsTest {

    @Test
    public  void test1(){
        String code = IdentifyingCodeUtils.createCode(6);
        System.out.println(code);
        code = IdentifyingCodeUtils.createCode(7);
        System.out.println(code);
        code = IdentifyingCodeUtils.createCode(8);
        System.out.println(code);
        code = IdentifyingCodeUtils.createCode(9);
        System.out.println(code);
    }

}
