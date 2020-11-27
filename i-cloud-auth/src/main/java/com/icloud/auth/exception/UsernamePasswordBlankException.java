package com.icloud.auth.exception;

import org.springframework.security.core.AuthenticationException;


/**
 * 用户名密码为空异常
 * @author 42806
 */
public class UsernamePasswordBlankException extends AuthenticationException {

    public UsernamePasswordBlankException(String msg) {
        super(msg);
    }

}
