package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.persistence.entity.dto.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void getUser() {
        System.out.println("testing");
        List<User> users = userService.getUser();
        if (users != null) {
            users.forEach(user -> logger.info(user.getUserinfo().toString()));
        }
    }
}