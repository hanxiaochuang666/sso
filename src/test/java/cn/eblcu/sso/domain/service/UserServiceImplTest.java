package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.domain.service.impl.RedisUtils;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.UserInfoApiModel;
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
    @Autowired
    private RedisUtils redisUtils;

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

        UserInfoApiModel userApiModel = new UserInfoApiModel();
        userApiModel.setEmail("1111111111@qq.com");
        userApiModel.setPassword("123123");
        userApiModel.setRegisterType("2");
        Map<String,Object> map = userService.addUser(userApiModel);
        log.info("注册结果======"+map.toString());

    }

    // 查询用户基本信息
    @Test
    public void getUser() {

        User user = new User();
        user.setLoginname("13327401234");
        user = userService.getUser(user);
        log.info("查询结果=======" + user.toString());
    }

    @Test
    public void bindUserTest() {
        UserInfoApiModel user = new UserInfoApiModel();
        user.setEmail("623233529@qq.com");
        user.setLoginname("hanchuang");
        user.setCode("CUIRLd");
        user.setBindType("2");
        user.setUserId(1);
        try {
            userService.bindUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editUserTest() {

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setNickname("xxxssswww");
        try {
            userService.editUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}