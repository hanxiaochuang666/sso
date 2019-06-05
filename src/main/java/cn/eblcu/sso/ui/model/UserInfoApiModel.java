package cn.eblcu.sso.ui.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class UserInfoApiModel implements Serializable {

    private static final long serialVersionUID = -6212417331253605213L;

    @ApiModelProperty(value = "用户id",notes = "绑定操作时为必传项！")
    private Integer userId;

    @ApiModelProperty(value = "登录账号",notes = "登录时为必传项（除手机短信登录）！")
    private String loginname;

    @ApiModelProperty(value = "密码",notes = "登录与注册时为必传项！")
    private String password;

    @ApiModelProperty(value = "手机号")
    private Long mobile;

    @Email
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @Max(value = 2)
    @Min(value = 1)
    @ApiModelProperty(value = "注册类型 1:手机号 2：邮箱",notes = "注册时为必传项！")
    private Integer registerType;

    @Max(value = 2)
    @Min(value = 1)
    @ApiModelProperty(value = "登录类型 1：手机号 2：用户名" ,notes = "登录时为必传项！")
    private Integer loginType;

    @Max(value = 2)
    @Min(value = 1)
    @ApiModelProperty(value = "绑定类型 1：手机绑定 2：邮箱绑定",notes = "绑定操作时为必传项！")
    private Integer bindType;

    @ApiModelProperty(value = "code")
    private String code;


}
