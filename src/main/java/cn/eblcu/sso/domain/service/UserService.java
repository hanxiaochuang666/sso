package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.persistence.entity.dto.User;

import java.util.List;

public interface UserService {
    public List<User> getUser();

    public void deleteUser(int id);

    public void addUser(User user);

}
