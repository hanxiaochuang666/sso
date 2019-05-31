package cn.eblcu.sso.persistence.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -2522840344126053929L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

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

    @ApiModelProperty(value = "状态 0:正常 -1:冻结")
    private Integer status;

    @ApiModelProperty(value = "注册日期")
    private Date registertime;

    @ApiModelProperty(value = "最后一次登录日期")
    private Date lastlogintime;

    @ApiModelProperty(value = "最后一次登录ip")
    private String lastloginip;

    @ApiModelProperty(value = "登录次数")
    private Integer count;

    @ApiModelProperty(value = "用户扩展信息")
    private UserInfo userinfo;

    public User(){}

    public User(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", mobile=" + mobile +
                ", email='" + email + '\'' +
                ", weiboid='" + weiboid + '\'' +
                ", wechatid='" + wechatid + '\'' +
                ", qqopenid='" + qqopenid + '\'' +
                ", status=" + status +
                ", registertime=" + registertime +
                ", lastlogintime=" + lastlogintime +
                ", lastloginip='" + lastloginip + '\'' +
                ", count=" + count +
                ", userinfo=" + userinfo +
                '}';
    }
}
