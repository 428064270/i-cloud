package com.icloud.rbac.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icloud.common.entitys.common.PageResult;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacResource;
import com.icloud.rbac.mapper.RbacResourceMapper;
import com.icloud.rbac.service.RbacResourceService;
import org.apache.commons.lang3.StringUtils;
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
    public PageResult<RbacResource> findListPage(QueryPageParam param) {
        QueryWrapper<RbacResource> wrapper = new QueryWrapper<>();
        wrapper.like("name", String.valueOf(param.getQueryValue("name")));

        String type = String.valueOf(param.getQueryValue("type"));
        if (!StringUtils.isBlank(type)) {
            wrapper.eq("type", String.valueOf(param.getQueryValue("type")));
        }
        Page page = this.mapper.selectPage(new Page<>(param.getPageNo(), param.getPageSize()), wrapper);
        return new PageResult<RbacResource>(page.getTotal(), page.getRecords());
    }

    @Override
    public List<Map<String, Object>> selectMenuOptions() {
        Map<String, Object> superMenu = new HashMap<>();
        superMenu.put("value", "0");
        superMenu.put("label", "顶级资源");
        superMenu.put("children", this.mapper.selectMenuOptions());
        return Arrays.asList(superMenu);
    }

    @Override
    public List<Map<String, Object>> selectAllOptions() {
        return this.mapper.selectAllOptions();
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

}
