package com.springbazaar.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException {
    public UserAuthenticationException(String msg) {
        super(msg);
    }

    public UserAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
