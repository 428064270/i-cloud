package com.icloud.auth.filter;

import com.alibaba.fastjson.JSON;
import com.icloud.common.components.common.RedisComponent;
import com.icloud.common.entitys.common.RedisKeySuffixStorage;
import com.icloud.common.entitys.rbac.RbacUser;
import com.icloud.common.feigns.FeignRbacProxy;
import com.icloud.common.utils.auth.JwtUtil;
import com.icloud.common.utils.auth.UrlMatcher;
import com.icloud.common.utils.http.HttpResponse;
import com.icloud.auth.exception.CodeAuthenticationException;
import com.icloud.auth.exception.UsernamePasswordBlankException;
import com.icloud.auth.service.RbacUserService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 自定义用户名密码获取方式、图形验证码认证以及登录成功失败后的响应方式
 *
 * @author 42806
 */
public class CodeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final RedisComponent redis;

    private final AuthenticationManager authenticationManager;

    private final RbacUserService rbacUserService;

    private final FeignRbacProxy feignRbacProxy;

    public CodeUsernamePasswordAuthenticationFilter(RedisComponent redis, AuthenticationManager authenticationManager, RbacUserService rbacUserService, FeignRbacProxy feignRbacProxy) {
        this.redis = redis;
        this.authenticationManager = authenticationManager;
        this.rbacUserService = rbacUserService;
        this.feignRbacProxy = feignRbacProxy;
    }

    /**
     * 自定义图形验证码认证及用户密码认证
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //从requestBody中获取出参数
        Map<String, String> params = JSON.parseObject(getRequestBodyData(request), Map.class);

        //获取用户输入图形验证码
        String code = params.containsKey("code") ? params.get("code") : "";
        //从Redis中获取图形验证码真实值并与用户输入的验证码进行比较
        HttpSession session = request.getSession();
        String authenticCode = String.valueOf(redis.get(session.getId() + RedisKeySuffixStorage.REDIS_IMAGE_CODE_KEY));
        if (!code.equalsIgnoreCase(authenticCode)) {
            //图形验证码验证失败使其失效
            redis.del(session.getId() + RedisKeySuffixStorage.REDIS_IMAGE_CODE_KEY);
            throw new CodeAuthenticationException("图形验证码错误");
        }
        //图形验证码验证完成后使其失效
        redis.del(session.getId() + RedisKeySuffixStorage.REDIS_IMAGE_CODE_KEY);

        //获取用户名进行参数合法校验
        String username = params.get("username");
        if (StringUtils.isBlank(username)) {
            throw new UsernamePasswordBlankException("用户名不能为空");
        }

        //获取密码进行参数合法校验
        String password = params.get("password");
        if (StringUtils.isBlank(password)) {
            throw new UsernamePasswordBlankException("密码不能为空");
        }

        //创建认证信息并执行认证操作
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 认证成功后执行的操作
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //获取用户信息，按照用户名称生成Token并响应
        String token = JwtUtil.sign(authResult.getName());
        //通过用户名称查询用户详细信息并以Token为key的形式存入Redis
        RbacUser user = this.rbacUserService.getUserByUsername(authResult.getName());
        this.redis.set(token, user, 60 * 60);
        //查询用户存在的接口权限
        HttpResponse<List<String>> httpResponse = this.feignRbacProxy.userApiList(user.getId());
        if (!httpResponse.getCode().equals(200)) {
            this.response(response, HttpResponse.error(httpResponse.getMsg()));
            return;
        }
        List<String> apiPathList = httpResponse.getData();
        StringBuffer includes = new StringBuffer();
        //将接口权限列表转成,分割，生成URL匹配工具并保存在Redis中由网关使用
        for (int i = 0; i < apiPathList.size(); i++) {
            includes.append(apiPathList.get(i));
            if (i != apiPathList.size() - 1) {
                includes.append(",");
            }
        }
        UrlMatcher matcher = new UrlMatcher(includes.toString(), "");
        this.redis.set(user.getId() + RedisKeySuffixStorage.REDIS_AUTH_MATCHER_KEY, matcher, 60 * 60);
        this.response(response, HttpResponse.success(token));
    }

    /**
     * 认证失败后执行的操作
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String message = failed.getMessage().equals("Bad credentials") ? "用户名或密码验证错误" : failed.getMessage();
        this.response(response, HttpResponse.error(message));
    }

    /**
     * 响应信息
     *
     * @param httpServletResponse
     * @param response
     */
    private void response(HttpServletResponse httpServletResponse, HttpResponse response) throws IOException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getWriter().write(com.alibaba.fastjson.JSON.toJSONString(response));
    }

    /**
     * 获取request中body数据
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getRequestBodyData(HttpServletRequest request) throws IOException {
        BufferedReader bufferReader = new BufferedReader(request.getReader());
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferReader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
