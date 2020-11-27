package com.icloud.security.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码校验异常
 *
 * @author 42806
 */
public class CodeAuthenticationException extends AuthenticationException {

    public CodeAuthenticationException(String msg) {
        super(msg);
    }

}
