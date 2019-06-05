package cn.eblcu.sso.domain.service;

import java.util.Map;

public interface IQqService {
    String getUrl();

    Map<String, Object> qqCallBack(String code, String state)throws Exception;

    Map<String,String> getValidState();
}
