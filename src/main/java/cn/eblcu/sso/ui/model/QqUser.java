package cn.eblcu.sso.ui.model;

import java.io.Serializable;

/**
 * @ClassName QqUser
 * @Author 焦冬冬
 * @Date 2019/5/14 17:43
 * QQ用户信息
 **/
public class QqUser implements Serializable {
    /**
     * 返回码 0为正常
     */
    private int ret;
    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;
    /**
     * 用户在QQ空间的昵称。
     */
    private String nickname;
    /**
     * 大小为30×30像素的QQ空间头像URL。
     */
    private String figureurl;
    /**
     * 大小为50×50像素的QQ空间头像URL。
     */
    private String figureurl_1;
    /**
     * 大小为100×100像素的QQ空间头像URL。
     */
    private String figureurl_2;
    /**
     * 大小为40×40像素的QQ头像URL。
     */
    private String figureurl_qq_1;
    /**
     * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
     */
    private String figureurl_qq_2;
    /**
     * 性别
     */
    private String gender;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl_1() {
        return figureurl_1;
    }

    public void setFigureurl_1(String figureurl_1) {
        this.figureurl_1 = figureurl_1;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public void setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
