package com.icloud.auth.service;

import com.icloud.common.entitys.rbac.RbacUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * t_view_rbac_user
 *
 * @author 42806
 */
public interface RbacUserService extends UserDetailsService {

    /**
     * 按照用户名查询用户
     *
     * @param username
     * @return
     */
    RbacUser getUserByUsername(String username);

}
