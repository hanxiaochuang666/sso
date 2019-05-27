package cn.eblcu.sso.persistence.entity.dto;

import cn.eblcu.sso.persistence.dao.UserInfoDao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@ApiModel(value = "用户基本信息")
public class User implements Serializable {

    private static final long serialVersionUID = -2522840344126053929L;

    @Autowired
    private UserInfoDao userInfoMapper;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "登录时间")
    private String loginname;

    @ApiModelProperty(value = "密码")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getLastloginip() {
        return lastloginip;
    }

    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public UserInfo getUserinfo() {
        UserInfo u = new UserInfo();
        u.setUserid(this.id);
        List<UserInfo> userinfos = userInfoMapper.selectUserInfoList(u);
        if (userinfos != null) {
            userinfo = userinfos.get(0);
        }
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(loginname, user.loginname) &&
                Objects.equals(password, user.password) &&
                Objects.equals(mobile, user.mobile) &&
                Objects.equals(email, user.email) &&
                Objects.equals(weiboid, user.weiboid) &&
                Objects.equals(wechatid, user.wechatid) &&
                Objects.equals(qqopenid, user.qqopenid) &&
                Objects.equals(status, user.status) &&
                Objects.equals(registertime, user.registertime) &&
                Objects.equals(lastlogintime, user.lastlogintime) &&
                Objects.equals(lastloginip, user.lastloginip) &&
                Objects.equals(count, user.count) &&
                Objects.equals(userinfo, user.userinfo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, loginname, password, mobile, email, weiboid, wechatid, qqopenid, status, registertime, lastlogintime, lastloginip, count, userinfo);
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
