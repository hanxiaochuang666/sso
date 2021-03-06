package cn.eblcu.sso;

import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.domain.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SsoApplication.class)
public class SsoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    public UserService userService;

    @Test
    public void getUser() {
        List<User> list = userService.getUserList();
        if (list != null && list.size() > 0) {
            for (User user : list) {
                System.out.println(user.getLoginname());
            }
        }else{
            System.out.println("空");
        }
    }

    @Test
    public void deleteById() {
        userService.deleteUser(1);
        System.out.println("成功");
    }

}
