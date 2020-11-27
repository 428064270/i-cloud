package com.icloud.rbac.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色资源数据访问
 *
 * @author 42806
 */
public interface RbacRoleResourceMapper {

    /**
     * 批量保存角色与资源关系
     *
     * @param roleId
     * @param resourceIds
     * @return
     */
    Integer insertBatch(@Param("roleId") String roleId, @Param("resourceIds") List<String> resourceIds);

    /**
     * 通过角色编号批量删除关联关系
     *
     * @param roleId
     * @return
     */
    Integer deleteBatchByRoleId(String roleId);

}
