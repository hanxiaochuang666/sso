package cn.eblcu.sso.infrastructure.util;

import cn.eblcu.sso.persistence.entity.dto.MailTemplate;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;


public class SendMailUtils {
    private SendMailUtils(){

    }
    /**
     * @Author 焦冬冬
     * @Description
     * @Date 10:00 2019/5/29
     * @Param 请在调用前校验所有字段
     * @return
     **/
    public static void sendMail(@Valid MailTemplate mailTemplate) throws Exception {
        //1.创建session对象
            //1.1配置session属性
            Properties prop = new Properties();
            //打开身份验证
            prop.setProperty("mail.smtp.auth", "true");
            //设置邮件服务器主机名
            prop.setProperty("mail.smtp.host", MailTemplate.EMAL_SMTP_HOST);
            //设置邮件服务器端口
            prop.setProperty("mail.smtp.port", MailTemplate.MAL_SMTP_PORT);
            // 发送邮件协议名称
            prop.setProperty("mail.transport.protocol", "smtp");
            /**SSL认证
             * 腾讯邮箱需要SSL认证
             * */
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            //设置是否使用ssl安全连接（一般都使用）
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            //1.2创建session
            Session session = Session.getInstance(prop);
        //2.创建message
            Message message = new MimeMessage(session);
            //2.1设置标题
            message.setSubject(mailTemplate.getMailTitle());
            //2.2设置发件人 邮箱 昵称  昵称字符集编码
            message.setFrom(new InternetAddress(mailTemplate.getFromMailAddress(),mailTemplate.getName(), "UTF-8"));
            //2.3设置内容
            message.setContent(mailTemplate.getContent(), "text/html;charset=UTF-8");
            //2.4设置收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(mailTemplate.getToMailAdress()));
            //2.5设置显示的发送时间
            message.setSentDate(new Date());
        //3.发送邮件
            Transport transport = session.getTransport();
            transport.connect(MailTemplate.EMAL_SMTP_HOST,mailTemplate.getFromMailAddress(),mailTemplate.getFromMailPassword());
            transport.sendMessage(message,message.getAllRecipients());
            //transport.send(message);
    }
}
