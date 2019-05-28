package cn.eblcu.sso.ui.model;


import cn.eblcu.sso.persistence.entity.dto.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @desc    与前端对接的用户信息模型
 * @Author：hanchuang
 * @Version 1.0
 * @Date：add on 13:18 2019/5/28
 */
@ApiModel(value = "用户基本信息")
public class UserApiModel implements Serializable {

    private static final long serialVersionUID = -5900949042720184521L;

    @ApiModelProperty(value = "user")
    private User user;

    @ApiModelProperty(value = "注册类型 1:手机号 2：邮箱",notes = "注册时为必传项！")
    private String registerType;

    @ApiModelProperty(value = "token")
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserApiModel)) return false;
        UserApiModel that = (UserApiModel) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(registerType, that.registerType) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, registerType, token);
    }

    @Override
    public String toString() {
        return "UserApiModel{" +
                "user=" + user.toString() +
                ", registerType='" + registerType + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
