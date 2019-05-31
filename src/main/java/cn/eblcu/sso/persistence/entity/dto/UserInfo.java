package cn.eblcu.sso.persistence.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1650745852979857066L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

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

    public UserInfo(){}

    public UserInfo(@NotNull(message = "${userInfo.userId.notNull}") Integer userid) {
        this.userid = userid;
    }
}
