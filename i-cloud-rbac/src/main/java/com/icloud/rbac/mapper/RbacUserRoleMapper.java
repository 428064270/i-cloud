package com.icloud.rbac.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色用户关联数据访问
 *
 * @author 42806
 */
public interface RbacUserRoleMapper {

    /**
     * 批量保存用户与角色关系
     *
     * @param userId
     * @param roleIds
     * @return
     */
    Integer insertBatch(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);

    /**
     * 通过用户编号批量删除关联关系
     *
     * @param userId
     * @return
     */
    Integer deleteBatchByUserId(String userId);

    /**
     * 通过角色编号批量删除关联关系
     *
     * @param roleId
     * @return
     */
    Integer deleteBatchByRoleId(String roleId);

}
