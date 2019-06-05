package cn.eblcu.sso.domain.service;

import java.util.Map;


public interface IWeChatService {

    Map<String, Object> landCallBack(String code, String state1) throws Exception;

    String  getUrl();
}
