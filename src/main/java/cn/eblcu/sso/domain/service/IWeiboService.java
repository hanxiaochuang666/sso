package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.ui.model.WeiBoUser;

public interface IWeiboService {
    String getUrl() throws DomainException;

    WeiBoUser weiboCallBack(String code, String state)throws Exception;
}
