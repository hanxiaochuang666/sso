package cn.eblcu.sso;

import cn.eblcu.sso.infrastructure.util.SendMailUtils;
import cn.eblcu.sso.persistence.entity.dto.MailTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName SendMailUtilsTest
 * @Author 焦冬冬
 * @Date 2019/5/29 10:30
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMailUtilsTest {
    @Test
    public void test1(){
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setFromMailAddress("jiaodongdong@blcu.edu.cn");
        //授权码需要在邮箱申请
        mailTemplate.setFromMailPassword("WSFAAaq6fC6uSHft");
        mailTemplate.setContent("测试短信");
        mailTemplate.setMailTitle("验证测试");
        mailTemplate.setName("jdd");
        mailTemplate.setToMailAdress("jiaodongdong@blcu.edu.cn");
        try {
            SendMailUtils.sendMail(mailTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
