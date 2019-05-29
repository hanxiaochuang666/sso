package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.ui.model.BaseModle;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName RegisterCheckServiceImplTest
 * @Author 焦冬冬
 * @Date 2019/5/29 17:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RegisterCheckServiceImplTest {
    @Resource(name="registerCheckService")
    private IRegisterCheckService registerCheckService;

    @Test
    public  void test1(){
        BaseModle baseModle = registerCheckService.sendEmailInfo("623233529@qq.com");
        System.out.println(baseModle);
    }
    @Test
    public void test2(){
        boolean flag = registerCheckService.checkRegister("jiaodongdong@blcu.edu.cn", "Bql4Bk");
        System.out.println(flag);
    }
}
