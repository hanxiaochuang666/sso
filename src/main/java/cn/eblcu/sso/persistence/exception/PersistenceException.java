package cn.eblcu.sso.persistence.exception;

import cn.eblcu.sso.ui.exception.MyBaseException;


public class PersistenceException extends MyBaseException {

    private static final long serialVersionUID = 7372284833659933834L;

    public PersistenceException(String message) {
        super(message);
    }
}
