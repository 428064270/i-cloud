package com.icloud.rbac.service.impl;

import com.alibaba.fastjson.JSON;
import com.icloud.common.entitys.common.PageResult;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacResource;
import com.icloud.common.entitys.rbac.RbacUser;
import com.icloud.common.utils.common.ListPageUtil;
import com.icloud.rbac.mapper.RbacUserMapper;
import com.icloud.rbac.mapper.RbacUserRoleMapper;
import com.icloud.rbac.service.RbacUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 用户业务逻辑
 *
 * @author 42806
 */
@Service
@Transactional
public class RbacUserServiceImpl implements RbacUserService {

    private final RbacUserMapper mapper;

    private final RbacUserRoleMapper rbacUserRoleMapper;

    private final BCryptPasswordEncoder encoder;

    public RbacUserServiceImpl(RbacUserMapper mapper, RbacUserRoleMapper rbacUserRoleMapper, BCryptPasswordEncoder encoder) {
        this.mapper = mapper;
        this.rbacUserRoleMapper = rbacUserRoleMapper;
        this.encoder = encoder;
    }

    @Override
    public boolean insert(RbacUser user) {
        try {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            user.setOptionValuesJson(JSON.toJSONString(user.getOptionValues()));
            user.setPassword(encoder.encode(user.getPassword()));
            this.mapper.insert(user);
            this.rbacUserRoleMapper.insertBatch(id, user.getOptionValues());
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        try {
            this.mapper.deleteById(id);
            this.rbacUserRoleMapper.deleteBatchByUserId(id);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateById(RbacUser user) {
        try {
            user.setOptionValuesJson(JSON.toJSONString(user.getOptionValues()));
            if (StringUtils.isBlank(user.getPassword())) {
                RbacUser dbUser = this.mapper.selectById(user.getId());
                user.setPassword(dbUser.getPassword());
            } else {
                user.setPassword(encoder.encode(user.getPassword()));
            }
            this.mapper.updateById(user);
            this.rbacUserRoleMapper.deleteBatchByUserId(user.getId());
            this.rbacUserRoleMapper.insertBatch(user.getId(), user.getOptionValues());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }


    @Override
    public PageResult<Map<String, Object>> findListPage(QueryPageParam param) {
        String name = String.valueOf(param.getQueryValue("name"));
        List<Map<String, Object>> list = this.mapper.selectListByName(name);
        return new PageResult<>(list.size(), ListPageUtil.listPaging(list, param.getPageNo(), param.getPageSize()));
    }
}
