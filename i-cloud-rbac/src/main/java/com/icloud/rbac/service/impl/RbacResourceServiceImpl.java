package com.icloud.rbac.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacResource;
import com.icloud.rbac.mapper.RbacResourceMapper;
import com.icloud.rbac.service.RbacResourceService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 42806
 */
@Service
public class RbacResourceServiceImpl implements RbacResourceService {

    private final RbacResourceMapper mapper;

    public RbacResourceServiceImpl(RbacResourceMapper mapper) {
        this.mapper = mapper;
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
        superMenu.put("children", this.getChildrens("0", list));
        return Arrays.asList(superMenu);
    }

//    @Override
//    public List<Map<String, Object>> selectAllOptions() {
//        return this.mapper.selectAllOptions();
//    }

    @Override
    public List<RbacResource> selectAllOptions() {
        //查询全部资源
        List<RbacResource> resourceList = this.mapper.selectList(new QueryWrapper<>());
        //将资源转成树形数据结构
        return this.getChildrens("0", resourceList);
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
    public List<Map<String, Object>> findMenuBatchByUserId(String userId) {
        return this.mapper.selectMenuBatchByUserId(userId);
    }

    /**
     * 获取子节点
     *
     * @param parentId
     * @param list
     * @return
     */
    public List<RbacResource> getChildrens(String parentId, List<RbacResource> list) {
        List<RbacResource> childrens = new ArrayList<>();
        for (RbacResource resource : list) {
            if (parentId.equals(resource.getParentId())) {
                //递归查找当前节点的子节点
                List<RbacResource> rChildrens = getChildrens(resource.getId(), list);
                resource.setChildren(rChildrens);
                childrens.add(resource);
            }
        }
        return childrens;
    }

}
