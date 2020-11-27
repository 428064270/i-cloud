package com.icloud.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.icloud.common.components.common.RedisComponent;
import com.icloud.common.entitys.common.RedisKeySuffixStorage;
import com.icloud.common.entitys.rbac.RbacUser;
import com.icloud.common.utils.auth.UrlMatcher;
import com.icloud.common.utils.http.HttpResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 资源拦截并认证 在Token认证之后
 *
 * @author 42806
 */
@Component
public class ResourceFilter extends ZuulFilter {

    @Resource(name = "TokenUrlMatcher")
    private UrlMatcher matcher;

    private final RedisComponent redis;

    public ResourceFilter(RedisComponent redis) {
        this.redis = redis;
    }

    @Override
    public String filterType() {
        return "pre";
    }


    @Override
    public int filterOrder() {
        return 1;
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
        String uri = request.getRequestURI();
        //从请求头中获取出Token再按照Token从Redis中获取出用户信息
        String token = request.getHeader("X-Token");
        RbacUser user = (RbacUser) this.redis.get(token);
        //从Redis中获取出URL匹配工具
        UrlMatcher matcher = (UrlMatcher) this.redis.get(user.getId() + RedisKeySuffixStorage.REDIS_AUTH_MATCHER_KEY);
        if (!matcher.matches(uri)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(200);
            requestContext.setResponseBody(JSON.toJSONString(HttpResponse.error("权限不足")));
            requestContext.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }
        return null;
    }


}
