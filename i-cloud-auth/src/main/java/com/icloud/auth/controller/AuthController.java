package com.icloud.auth.controller;

import com.icloud.common.components.annotation.Permission;
import com.icloud.common.components.base.BaseController;
import com.icloud.common.components.common.RedisComponent;
import com.icloud.common.entitys.common.RedisKeySuffixStorage;
import com.icloud.common.utils.auth.CodeUtil;
import com.icloud.common.utils.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 42806
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private final RedisComponent redis;

    public AuthController(RedisComponent redis) {
        super("AuthController");
        this.redis = redis;
    }

    /**
     * 获取图形验证码
     *
     * @return
     */
    @GetMapping("/code")
    @Permission(description = "获取图形验证码", operation = Permission.Operation.SELECT)
    public HttpResponse<?> code(HttpSession session) {
        CodeUtil.Validate validate = CodeUtil.getRandomCode();
        redis.set(session.getId() + RedisKeySuffixStorage.REDIS_IMAGE_CODE_KEY, validate.getValue());
        return HttpResponse.success(validate.getBase64Str());
    }

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @Permission(description = "用户登出", operation = Permission.Operation.SELECT)
    public HttpResponse<?> logout(HttpServletRequest request) {
        //从请求头中获取Token信息
        String token = request.getHeader("X-Token");
        //从Redis中删除Token信息使其失效
        this.redis.del(token);
        return HttpResponse.success();
    }

}
