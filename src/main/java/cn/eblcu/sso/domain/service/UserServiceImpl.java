package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.entity.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userMapper;

    @Override
    public List<User> getUser() {
        return userMapper.selectUserList(new User());
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public void addUser(User user) {

    }
}
