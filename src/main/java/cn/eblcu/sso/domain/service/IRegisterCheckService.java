package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.ui.model.BaseModle;

public interface IRegisterCheckService {
    /**
     * @Author 焦冬冬
     * @Description  发送邮件验证信息
     * @Date 15:35 2019/5/29
     * @Param emailStr 邮箱号
     * @return
     **/
    BaseModle sendEmailInfo(String emailStr);

    /**
     * @Author 焦冬冬
     * @Description 发送手机验证码
     * @Date 15:36 2019/5/29
     * @Param   phoneNumber 手机号
     * @return
     **/
    BaseModle sendPhoneInfo(String phoneNumber);

    /**
     * @Author 焦冬冬
     * @Description 校验验证码
     * @Date 15:38 2019/5/29
     * @Param
     *          account  账号
     *          code     验证码
     * @return
     **/
    boolean checkRegister(String account,String code);
}
