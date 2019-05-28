package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.ui.model.UserApiModel;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getUserList();

    User getUser(User user);

    void deleteUser(int id);

    Map<String,Object> addUser(UserApiModel user) throws Exception;

}
