package cn.eblcu.sso.persistence.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @desc
 * @Author：hanchuang
 * @Version 1.0
 * @Date：add on 9:21 2019/5/27
 */
@Component
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1650745852979857066L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @NotNull(message = "${userInfo.userId.notNull}")
    @ApiModelProperty(value = "外键 用户基本信息id")
    private Integer userid;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String headphoto;

    @ApiModelProperty(value = "性别 0：女  1：男")
    private Integer gender;

    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @ApiModelProperty(value = "省份id")
    private String provinceid;

    @ApiModelProperty(value = "城市id")
    private String cityid;

    @ApiModelProperty(value = "乡镇id")
    private String townid;

    @ApiModelProperty(value = "学历（0：无 1：专科 2：本科 3：硕士 4：博士及以上）")
    private Integer education;

    @ApiModelProperty(value = "QQ号码")
    private String qq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadphoto() {
        return headphoto;
    }

    public void setHeadphoto(String headphoto) {
        this.headphoto = headphoto;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getTownid() {
        return townid;
    }

    public void setTownid(String townid) {
        this.townid = townid;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        UserInfo userInfo = (UserInfo) o;
        return education == userInfo.education &&
                Objects.equals(id, userInfo.id) &&
                Objects.equals(userid, userInfo.userid) &&
                Objects.equals(nickname, userInfo.nickname) &&
                Objects.equals(headphoto, userInfo.headphoto) &&
                Objects.equals(gender, userInfo.gender) &&
                Objects.equals(realname, userInfo.realname) &&
                Objects.equals(provinceid, userInfo.provinceid) &&
                Objects.equals(cityid, userInfo.cityid) &&
                Objects.equals(townid, userInfo.townid) &&
                Objects.equals(qq, userInfo.qq);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userid, nickname, headphoto, gender, realname, provinceid, cityid, townid, education, qq);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userid=" + userid +
                ", nickname='" + nickname + '\'' +
                ", headphoto='" + headphoto + '\'' +
                ", gender=" + gender +
                ", realname='" + realname + '\'' +
                ", provinceid='" + provinceid + '\'' +
                ", cityid='" + cityid + '\'' +
                ", townid='" + townid + '\'' +
                ", education=" + education +
                ", qq='" + qq + '\'' +
                '}';
    }
}
