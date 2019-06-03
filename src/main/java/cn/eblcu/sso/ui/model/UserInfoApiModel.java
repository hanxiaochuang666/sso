package cn.eblcu.sso.ui.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoApiModel implements Serializable {

    private static final long serialVersionUID = -6212417331253605213L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "登录账号")
    private String loginname;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @ApiModelProperty(value = "手机号")
    private Long mobile;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "注册类型 1:手机号 2：邮箱",notes = "注册时为必传项！")
    private String registerType;

    @ApiModelProperty(value = "登录类型 1：手机号 2：邮箱" ,notes = "登录时为必传项！")
    private String loginType;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "bindType 1：手机绑定 2：邮箱绑定")
    private String bindType;


}
