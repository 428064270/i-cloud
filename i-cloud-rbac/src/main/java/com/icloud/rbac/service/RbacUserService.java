package com.icloud.rbac.service;

import com.icloud.common.entitys.common.PageResult;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacResource;
import com.icloud.common.entitys.rbac.RbacUser;

import java.util.List;
import java.util.Map;

/**
 * 系统用户业务逻辑
 *
 * @author 42806
 */
public interface RbacUserService {

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    boolean insert(RbacUser user);

    /**
     * 通过编号删除用户
     *
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 通过用户编号修改用户信息
     *
     * @param user
     * @return
     */
    boolean updateById(RbacUser user);


    /**
     * 通过用户名称模糊查询用户并分页
     *
     * @param param
     * @return
     */
    PageResult<Map<String, Object>> findListPage(QueryPageParam param);

}
