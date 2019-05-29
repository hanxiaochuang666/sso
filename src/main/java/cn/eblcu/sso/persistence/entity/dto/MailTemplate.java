package cn.eblcu.sso.persistence.entity.dto;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName MailTemplate
 * @Author 焦冬冬
 * @Date 2019/5/29 9:41
 **/
public class MailTemplate{
    /**
     * 腾讯发送服务器
     */
    public static final String EMAL_SMTP_HOST="smtp.exmail.qq.com";
    /**
     * 腾讯发送服务器端口
     */
    public static final String MAL_SMTP_PORT="465";
    /**
     * 发送人邮箱
     */
    @NotBlank(message = "发送人邮箱不能为空")
    private String fromMailAddress;
    /**
     * 发送人邮箱授权码
     */
    @NotBlank(message = "发送人邮箱授权码不能为空")
    private String fromMailPassword;
    /**
     * 接收人邮箱
     */
    @NotBlank(message = "接收人邮箱不能为空")
    private String toMailAdress;
    /**
     * 邮件标题
     */
    @NotBlank(message = "邮件标题不能为空")
    private String mailTitle;
    /**
     * 接受邮件时显示的昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String name;

    /**
     * 邮件内容
     */
    @NotBlank(message = "邮件内容不能为空")
    private String content;

    public String getFromMailAddress() {
        return fromMailAddress;
    }

    public void setFromMailAddress(String fromMailAddress) {
        this.fromMailAddress = fromMailAddress;
    }

    public String getFromMailPassword() {
        return fromMailPassword;
    }

    public void setFromMailPassword(String fromMailPassword) {
        this.fromMailPassword = fromMailPassword;
    }

    public String getToMailAdress() {
        return toMailAdress;
    }

    public void setToMailAdress(String toMailAdress) {
        this.toMailAdress = toMailAdress;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
