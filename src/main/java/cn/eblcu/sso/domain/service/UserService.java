package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.persistence.entity.dto.UserInfo;
import cn.eblcu.sso.ui.model.UserInfoApiModel;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 查询用户列表
     * @return
     */
    List<User> getUserList();

    /**
     * 根据条件查询用户信息
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 根据主键删除用户信息（修改状态为不可用）
     * @param id
     */
    void deleteUser(int id);

    /**
     * 注册使用
     * @param user
     * @return
     * @throws Exception
     */
    Map<String,Object> addUser(UserInfoApiModel user) throws Exception;

    /**
     * 用户信息完善
     * @param user
     * @throws Exception
     */
    void editUser(User user) throws Exception;

    /**
     * 用户信息绑定
     * @param user
     * @throws Exception
     */
    void bindUser(UserInfoApiModel user) throws Exception;

    /**
     * 用户拓展信息编辑
     * @param userInfo
     * @throws Exception
     */
    void editUserInfo(UserInfo userInfo) throws Exception;

}
