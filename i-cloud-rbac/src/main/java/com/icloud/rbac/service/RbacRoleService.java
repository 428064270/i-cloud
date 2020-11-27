package com.icloud.rbac.service;

import com.icloud.common.entitys.common.PageResult;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacRole;

import java.util.List;

/**
 * 角色业务逻辑
 *
 * @author 42806
 */
public interface RbacRoleService {

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    boolean insert(RbacRole role);

    /**
     * 通过编号删除角色
     *
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 通过角色编号修改角色信息
     *
     * @param role
     * @return
     */
    boolean updateById(RbacRole role);

    /**
     * 分页查询角色
     *
     * @return 分页后的角色信息
     */
    PageResult<RbacRole> findListPage(QueryPageParam param);

    /**
     * 下拉框数据，查询全部角色
     *
     * @return
     */
    List<RbacRole> selectOptions();

}
