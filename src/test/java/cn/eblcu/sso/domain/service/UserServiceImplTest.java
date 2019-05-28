package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.ui.model.UserApiModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {


    @Autowired
    private UserService userService;

    @Test
    public void getUserList() {

        List<User> users = userService.getUserList();
        if (users != null && users.size() > 0) {
            users.forEach(user -> log.info("查询结果====="+user.toString()));
        }
    }

    // 注册用户
    @Test
    public void addUser() throws Exception{

        UserApiModel userApiModel = new UserApiModel();
        User user = new User();
        user.setMobile(13327401234L);
        user.setPassword("123456");
        userApiModel.setUser(user);
        userApiModel.setRegisterType("1");
        Map<String,Object> map = userService.addUser(userApiModel);
        log.info("测试结果======"+map.toString());

    }

    // 查询用户基本信息
    @Test
    public void getUser() {

        User user = new User();
        user.setLoginname("13327401234");
        user = userService.getUser(user);
        log.info("查询结果=======" + user.toString());
    }
}