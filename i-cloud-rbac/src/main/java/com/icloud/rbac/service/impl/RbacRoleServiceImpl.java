package com.icloud.rbac.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icloud.common.entitys.common.PageResult;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacRole;
import com.icloud.rbac.mapper.RbacRoleMapper;
import com.icloud.rbac.mapper.RbacRoleResourceMapper;
import com.icloud.rbac.mapper.RbacUserRoleMapper;
import com.icloud.rbac.service.RbacRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 42806
 */
@Service
@Transactional
public class RbacRoleServiceImpl implements RbacRoleService {

    private final RbacRoleMapper mapper;

    private final RbacRoleResourceMapper rbacRoleResourceMapper;

    private final RbacUserRoleMapper rbacUserRoleMapper;

    public RbacRoleServiceImpl(RbacRoleMapper mapper, RbacRoleResourceMapper rbacRoleResourceMapper, RbacUserRoleMapper rbacUserRoleMapper) {
        this.mapper = mapper;
        this.rbacRoleResourceMapper = rbacRoleResourceMapper;
        this.rbacUserRoleMapper = rbacUserRoleMapper;
    }

    @Override
    public boolean insert(RbacRole role) {
        try {
            String roleId = UUID.randomUUID().toString();
            //保存角色
            role.setId(roleId);
            role.setOptionValuesJson(JSON.toJSONString(role.getOptionValues()));
            this.mapper.insert(role);
            //获取用户选择资源权限并进行绑定
            List<String> resourceIds = this.getResourceIds(role.getOptionValues());
            this.rbacRoleResourceMapper.insertBatch(roleId, resourceIds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {
            //删除角色信息
            this.mapper.deleteById(id);
            //删除角色资源绑定信息
            this.rbacRoleResourceMapper.deleteBatchByRoleId(id);
            //删除关联用户信息
            this.rbacUserRoleMapper.deleteBatchByRoleId(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public boolean updateById(RbacRole role) {
        try {
            //按照编号修改角色信息
            role.setOptionValuesJson(JSON.toJSONString(role.getOptionValues()));
            this.mapper.updateById(role);
            //删除角色存在资源绑定信息
            this.rbacRoleResourceMapper.deleteBatchByRoleId(role.getId());
            //重新获取用户选择资源权限并进行绑定
            List<String> resourceIds = this.getResourceIds(role.getOptionValues());
            this.rbacRoleResourceMapper.insertBatch(role.getId(), resourceIds);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public PageResult<RbacRole> findListPage(QueryPageParam param) {
        QueryWrapper<RbacRole> wrapper = new QueryWrapper<>();
        wrapper.like("name", String.valueOf(param.getQueryValue("name")));
        Page page = this.mapper.selectPage(new Page<>(param.getPageNo(), param.getPageSize()), wrapper);
        return new PageResult<RbacRole>(page.getTotal(), page.getRecords());
    }

    @Override
    public List<RbacRole> selectOptions() {
        return this.mapper.selectList(new QueryWrapper<>());
    }

    /**
     * 对从前端传入的资源编号进行处理
     *
     * @param optionValues
     * @return
     */
    private List<String> getResourceIds(List<List<String>> optionValues) {
        List<String> resourceIds = new ArrayList<>();
        for (List<String> values : optionValues) {
            for (String value : values) {
                if (!resourceIds.contains(value)) {
                    resourceIds.add(value);
                }
            }
        }
        return resourceIds;
    }

}
