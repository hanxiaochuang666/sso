package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.domain.service.impl.RedisUtils;
import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.BaseModle;
import cn.eblcu.sso.ui.model.UserInfoApiModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Resource(name="registerCheckService")
    private IRegisterCheckService registerCheckService;
    @Autowired
    private UserDao userDao;

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

        // 1、先发邮箱验证码
        BaseModle baseModle = registerCheckService.sendEmailInfo("623233529@qq.com");
        System.out.println(baseModle);
        // 2、然后输入验证码进行注册
        UserInfoApiModel userApiModel = new UserInfoApiModel();
        userApiModel.setEmail("623233529@qq.com");
        userApiModel.setPassword("123123");
        userApiModel.setRegisterType("2");// 邮箱注册
        userApiModel.setCode("E3r4t5");
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

        // 1、先发邮箱验证码
        BaseModle baseModle = registerCheckService.sendEmailInfo("623233529@qq.com");
        System.out.println(baseModle);
        // 2、绑定操作
        UserInfoApiModel user = new UserInfoApiModel();
        user.setEmail("623233529@qq.com");
        user.setLoginname("hanchuang");
        user.setCode("CUIRLd");// 绑定邮箱需要输入邮箱的验证码
        user.setBindType("2");// 绑定类型为邮箱
        user.setUserId(1);
        try {
            userService.bindUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editUserTest() {

        User user = new User();
        user.setId(6);
        user.setEmail("hanhan@169.com");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname("QaQ");
        user.setUserinfo(userInfo);
        try {
            userService.editUser(user);
            System.out.println("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}