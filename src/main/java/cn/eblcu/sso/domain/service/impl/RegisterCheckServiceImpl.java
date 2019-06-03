package cn.eblcu.sso.domain.service.impl;

import cn.eblcu.sso.domain.service.IRegisterCheckService;
import cn.eblcu.sso.infrastructure.util.IdentifyingCodeUtils;
import cn.eblcu.sso.infrastructure.util.SendMailUtils;
import cn.eblcu.sso.infrastructure.util.StringUtils;
import cn.eblcu.sso.persistence.dao.UserDao;
import cn.eblcu.sso.persistence.entity.dto.MailTemplate;
import cn.eblcu.sso.persistence.entity.dto.User;
import cn.eblcu.sso.ui.model.BaseModle;
import cn.eblcu.sso.ui.model.StatusCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("registerCheckService")
public class RegisterCheckServiceImpl implements IRegisterCheckService {
    private Logger logger = LoggerFactory.getLogger(RegisterCheckServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;

    @Value("${email.fromMailAddress}")
    private String fromMailAddress;

    @Value("${email.fromMailPassword}")
    private String fromMailPassword;

    @Value("${email.name}")
    private String name;

    @Value("${email.mailTitle}")
    private String mailTitle;

    @Value("${email.outTime}")
    private String outTime;

    @Autowired
    private UserDao userDao;

    @Override
    public BaseModle sendEmailInfo(String emailStr) {
        //1.校验当前账号是否已经注册
        if(isUsed(emailStr)){
            logger.info(emailStr+"已经注册");
            return BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),emailStr+"邮箱已经注册");
        }
        //防止重复点击
        if(redisUtils.isexists(emailStr)){
            logger.info(emailStr+"正在处理，请稍等");
            return BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),emailStr+"正在处理，请稍等");
        }
        //2.生成6位验证码
        String code = IdentifyingCodeUtils.createCode(6);
        logger.info("生成的验证码:"+code);
        //3.放入redis缓存
        int seconed = Integer.valueOf(outTime).intValue();
        redisUtils.setStrByTime(emailStr,code,seconed);
        //4.发送验证码
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setToMailAdress(emailStr);
        mailTemplate.setContent("验证码："+code+"("+(seconed/60)+"分钟内有效)");
        mailTemplate.setFromMailPassword(fromMailPassword);
        mailTemplate.setFromMailAddress(fromMailAddress);
        mailTemplate.setMailTitle(mailTitle);
        mailTemplate.setName(name);
        try {
            SendMailUtils.sendMail(mailTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("发送验证码失败");
            redisUtils.delByKey(emailStr);
            return BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),"验证码发送失败");

        }
        return  BaseModle.getSuccessData();
    }

    @Override
    public BaseModle sendPhoneInfo(String phoneNumber) {
        return BaseModle.getSuccessData();
    }

    private boolean isUsed(String account){
        User user = new User();
        user.setLoginname(account);
        List<User> users = userDao.selectUserList(user);
        return (StringUtils.isEmpty(user)||users.size()<=0)?false:true;
    }
    @Override
    public boolean checkRegister(String account, String code) {
        if(!redisUtils.isexists(account))
            return false;
        String value = redisUtils.getByKey(account);
        return value.equals(code)?true:false;
    }
}
