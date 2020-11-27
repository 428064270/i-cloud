package com.icloud.common.entitys.params;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 42806
 */
@Data
public class AuthParam {

    @NotNull(message = "用户名不能为空")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotNull(message = "图形验证码不能为空")
    @NotEmpty(message = "图形验证码不能为空")
    private String code;


}
