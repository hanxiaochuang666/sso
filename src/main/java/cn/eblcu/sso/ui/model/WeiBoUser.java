package cn.eblcu.sso.ui.model;

import java.io.Serializable;

/**
 * @ClassName WeiBoUser
 * @Author 焦冬冬
 * @Date 2019/5/16 10:55
 **/
public class WeiBoUser implements Serializable {
    private String idstr;       //字符串型的用户UID,用户的唯一标识
    private String screen_name; //用户昵称
    private String location;    //用户所在地
    private String profile_image_url;   //用户头像地址（中图），50×50像素
    private String gender;      //性别，m：男、f：女、n：未知
    private String created_at;  //用户创建（注册）时间

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

