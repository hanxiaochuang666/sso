package cn.eblcu.sso.domain.service;

import cn.eblcu.sso.domain.exception.DomainException;

import java.util.Map;

public interface IWeiboService {
    String getUrl() throws DomainException;

    Map<String, Object> weiboCallBack(String code, String state)throws Exception;
}
