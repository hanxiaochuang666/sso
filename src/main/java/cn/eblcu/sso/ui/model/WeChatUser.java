package cn.eblcu.sso.ui.model;

import java.io.Serializable;

/**
 * @ClassName WeChatUser
 * @Author 焦冬冬
 * @Date 2019/5/13 17:21
 **/
public class WeChatUser implements Serializable {
    private String openid; //普通开发者账号id,对于同一个appid下是唯一的
    private String nickname;
    private int sex;//普通用户性别，1为男性，2为女性
    private String province;
    private String city;
    private String country; //国家
    private String headimgurl; //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
    private String privilege; //用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
    private String unionid; //用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
