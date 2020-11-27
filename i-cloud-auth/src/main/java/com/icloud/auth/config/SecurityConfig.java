package com.icloud.auth.config;

import com.icloud.auth.filter.CodeUsernamePasswordAuthenticationFilter;
import com.icloud.common.components.common.RedisComponent;
import com.icloud.common.feigns.FeignRbacProxy;
import com.icloud.auth.service.RbacUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 42806
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RbacUserService userService;

    private final RedisComponent redisComponent;

    private final RbacUserService rbacUserService;

    private final FeignRbacProxy feignRbacProxy;

    public SecurityConfig(RbacUserService userService, RedisComponent redisComponent, RbacUserService rbacUserService, FeignRbacProxy feignRbacProxy) {
        this.userService = userService;
        this.redisComponent = redisComponent;
        this.rbacUserService = rbacUserService;
        this.feignRbacProxy = feignRbacProxy;
    }

    /**
     * 配置用户及认证方式
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //手动配置用户
        //auth.inMemoryAuthentication().withUser("zhaotianyu").password(getBCryptPasswordEncoder().encode("112233")).roles("admin");
        //从数据库查询用户并指定密码加密方式
        auth.userDetailsService(userService).passwordEncoder(getBCryptPasswordEncoder());
    }


    /**
     * 安全配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭cors和csrf
        http.cors().disable().csrf().disable();
        //放行/auth下的URL，其他的需要认证
        http.authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest().authenticated();
        //开启表单登录，默认登录地址为/login
        http.formLogin();
        //添加过滤器进行用户名密码自定义验证
        http.addFilter(new CodeUsernamePasswordAuthenticationFilter(this.redisComponent, super.authenticationManager(), rbacUserService, feignRbacProxy));
    }


    /**
     * 配置密码加密方式
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
