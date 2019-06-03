package cn.eblcu.sso.domain.exception;

import cn.eblcu.sso.ui.exception.MyBaseException;


public class DomainException extends MyBaseException {


    private static final long serialVersionUID = -6393988074026824209L;

    public DomainException(String message) {
        super(message);
    }
}
