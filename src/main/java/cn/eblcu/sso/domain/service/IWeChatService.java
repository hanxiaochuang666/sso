package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.ui.model.WeChatUser;


public interface IWeChatService {

    WeChatUser landCallBack(String code, String state1) throws DomainException;

    String  getUrl();
}
