package com.icloud.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icloud.auth.mapper.RbacUserMapper;
import com.icloud.common.entitys.rbac.RbacUser;
import com.icloud.auth.service.RbacUserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 42806
 */
@Service
public class RbacUserServiceImpl implements RbacUserService {

    private final RbacUserMapper mapper;

    public RbacUserServiceImpl(RbacUserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RbacUser user = this.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("admin"));
    }


    @Override
    public RbacUser getUserByUsername(String username) {
        QueryWrapper<RbacUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        RbacUser user = this.mapper.selectOne(wrapper);
        return user;
    }

}
