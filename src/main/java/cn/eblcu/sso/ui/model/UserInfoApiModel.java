package cn.eblcu.sso.ui.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @desc
 * @Author：hanchuang
 * @Version 1.0
 * @Date：add on 15:25 2019/5/29
 */
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

    @ApiModelProperty(value = "微博ID")
    private String weiboid;

    @ApiModelProperty(value = "微信ID")
    private String wechatid;

    @ApiModelProperty(value = "QQID")
    private String qqopenid;

    @ApiModelProperty(value = "注册类型 1:手机号 2：邮箱",notes = "注册时为必传项！")
    private String registerType;

    @ApiModelProperty(value = "登录类型 1：手机号 2：邮箱" +
            "3：qq 4：微信 5：微博",notes = "登录时为必传项！")
    private String loginType;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "bindType 1：手机绑定 2：邮箱绑定")
    private String bindType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeiboid() {
        return weiboid;
    }

    public void setWeiboid(String weiboid) {
        this.weiboid = weiboid;
    }

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    public String getQqopenid() {
        return qqopenid;
    }

    public void setQqopenid(String qqopenid) {
        this.qqopenid = qqopenid;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    @Override
    public String toString() {
        return "UserInfoApiModel{" +
                "userId=" + userId +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", mobile=" + mobile +
                ", email='" + email + '\'' +
                ", weiboid='" + weiboid + '\'' +
                ", wechatid='" + wechatid + '\'' +
                ", qqopenid='" + qqopenid + '\'' +
                ", registerType='" + registerType + '\'' +
                ", loginType='" + loginType + '\'' +
                ", token='" + token + '\'' +
                ", code='" + code + '\'' +
                ", bindType='" + bindType + '\'' +
                '}';
    }
}
