package cn.eblcu.sso.domain.service;


import cn.eblcu.sso.ui.model.QqUser;

import java.util.Map;

public interface IQqService {
    String getUrl();

    QqUser qqCallBack(String code, String state)throws Exception;

    Map<String,String> getValidState();
}
