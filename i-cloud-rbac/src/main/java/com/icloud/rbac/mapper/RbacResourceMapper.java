package com.icloud.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icloud.common.entitys.rbac.RbacResource;

import java.util.List;
import java.util.Map;

/**
 * 系统资源数据访问
 *
 * @author 42806
 */
public interface RbacResourceMapper extends BaseMapper<RbacResource> {

    /**
     * 按照名称一对多查询资源
     *
     * @param name
     * @return
     */
    List<RbacResource> selectToManyBatchByName(String name);

    /**
     * 按照用户编号查询用户存在的接口权限
     *
     * @param userId
     * @return
     */
    List<String> selectApiPathBatchByUserId(String userId);

    /**
     * 按照用户编号查询用户存在的菜单权限
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectMenuBatchByUserId(String userId);

}
