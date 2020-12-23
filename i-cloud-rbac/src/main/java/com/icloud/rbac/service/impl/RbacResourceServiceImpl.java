package com.icloud.rbac.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacResource;
import com.icloud.rbac.mapper.RbacResourceMapper;
import com.icloud.rbac.mapper.RbacRoleResourceMapper;
import com.icloud.rbac.service.RbacResourceService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 42806
 */
@Service
public class RbacResourceServiceImpl implements RbacResourceService {

    private final RbacResourceMapper mapper;

    private final RbacRoleResourceMapper roleResourceMapper;

    public RbacResourceServiceImpl(RbacResourceMapper mapper, RbacRoleResourceMapper roleResourceMapper) {
        this.mapper = mapper;
        this.roleResourceMapper = roleResourceMapper;
    }

    @Override
    public boolean insert(RbacResource resource) {
        resource.setId(UUID.randomUUID().toString());
        resource.setParentId(resource.getOptionValues().get(resource.getOptionValues().size() - 1));
        resource.setOptionValuesJson(JSON.toJSONString(resource.getOptionValues()));
        return this.mapper.insert(resource) > 0;
    }

    @Override
    public boolean deleteById(String id) {
        //查询全部资源
        List<RbacResource> list = this.mapper.selectList(new QueryWrapper<>());
        //获取当前需要删除资源的子资源并挨个删除
        List<RbacResource> children = this.getChildren(id, list);
        //删除用户选中资源
        this.delChildren(children);
        return this.mapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateById(RbacResource resource) {
        resource.setParentId(resource.getOptionValues().get(resource.getOptionValues().size() - 1));
        resource.setOptionValuesJson(JSON.toJSONString(resource.getOptionValues()));
        return this.mapper.updateById(resource) > 0;
    }

    @Override
    public List<Map<String, Object>> selectMenuOptions() {
        //查询菜单资源
        QueryWrapper<RbacResource> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 0);
        List<RbacResource> list = this.mapper.selectList(wrapper);
        //合并数据
        Map<String, Object> superMenu = new HashMap<>();
        superMenu.put("id", "0");
        superMenu.put("name", "顶级资源");
        superMenu.put("children", this.getChildren("0", list));
        return Arrays.asList(superMenu);
    }

    @Override
    public List<RbacResource> selectAllOptions() {
        //查询全部资源
        List<RbacResource> resourceList = this.mapper.selectList(new QueryWrapper<>());
        //将资源转成树形数据结构
        return this.getChildren("0", resourceList);
    }

    @Override
    public List<RbacResource> findToManyBatchByName(QueryPageParam param) {
        String name = String.valueOf(param.getQueryValue("name"));
        return this.mapper.selectToManyBatchByName(name);
    }

    @Override
    public List<String> findApiPathBatchByUserId(String userId) {
        return this.mapper.selectApiPathBatchByUserId(userId);
    }

    @Override
    public List<RbacResource> findMenuListByUserId(String userId) {
        List<RbacResource> list = this.mapper.selectMenuListByUserId(userId);
        //将用户拥有菜单资源转成树形数据结构
        return this.getChildren("0", list);
    }

    /**
     * 获取子节点
     *
     * @param parentId
     * @param list
     * @return
     */
    public List<RbacResource> getChildren(String parentId, List<RbacResource> list) {
        List<RbacResource> children = new ArrayList<>();
        for (RbacResource resource : list) {
            if (parentId.equals(resource.getParentId())) {
                //递归查找当前节点的子节点
                List<RbacResource> rChildrens = getChildren(resource.getId(), list);
                resource.setChildren(rChildrens);
                children.add(resource);
            }
        }
        return children;
    }

    /**
     * 删除子资源
     *
     * @param children
     */
    private void delChildren(List<RbacResource> children) {
        for (RbacResource resource : children) {
            if (resource.getChildren().size() > 0) {
                //删除当前资源的子资源
                this.delChildren(resource.getChildren());
            }
            //删除当前资源
            this.mapper.deleteById(resource.getId());
            //删除资源与角色关联信息
            this.roleResourceMapper.deleteBatchByResourceId(resource.getId());
        }
    }

}
