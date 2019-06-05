package cn.eblcu.sso.ui.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserToken
 * @Author 焦冬冬
 * @Date 2019/6/3 17:30
 **/
@Data
public class UserToken implements Serializable {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 1.认证字符串，在登陆的时候需要传
     */
    private String authenStr;

    /**
     * 2.请求段token验证
     */
    private String tokenCheckStr;
}
