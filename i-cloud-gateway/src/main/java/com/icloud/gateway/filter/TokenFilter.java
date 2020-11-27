package com.icloud.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.icloud.common.components.common.RedisComponent;
import com.icloud.common.utils.auth.UrlMatcher;
import com.icloud.common.utils.http.HttpResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Token拦截并认证
 *
 * @author 42806
 */
@Component
public class TokenFilter extends ZuulFilter {

    @Resource(name = "TokenUrlMatcher")
    private UrlMatcher matcher;

    private final RedisComponent redis;

    public TokenFilter(RedisComponent redis) {
        this.redis = redis;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否运行run方法，true运行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        //如果当前路径不在白名单内 开启拦截
        return !matcher.matches(uri);
    }

    @Override
    public Object run() throws ZuulException {
        //获取Request对象并获取URI
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //从请求头中获取出Token并对Token进行验证
        String token = request.getHeader("X-Token");
        if (!(token != null && redis.hasKey(token))) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(200);
            requestContext.setResponseBody(JSON.toJSONString(HttpResponse.error("请登录后再次尝试此操作")));
            requestContext.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }
        return null;
    }

}
