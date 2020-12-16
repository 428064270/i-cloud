package com.icloud.rbac.service;

import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacResource;

import java.util.List;
import java.util.Map;

/**
 * 系统资源业务逻辑
 *
 * @author 42806
 */
public interface RbacResourceService {

    /**
     * 添加资源
     *
     * @param resource
     * @return
     */
    boolean insert(RbacResource resource);

    /**
     * 通过编号删除资源
     *
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 通过资源编号修改资源信息
     *
     * @param resource
     * @return
     */
    boolean updateById(RbacResource resource);


    /**
     * 查询菜单选择框数据
     *
     * @return
     */
    List<Map<String, Object>> selectMenuOptions();

    /**
     * 查询全部菜单选择框数据
     *
     * @return
     */
    List<RbacResource> selectAllOptions();

    /**
     * 按照名称一对多查询资源
     *
     * @param param
     * @return
     */
    List<RbacResource> findToManyBatchByName(QueryPageParam param);

    /**
     * 按照用户编号查询用户存在的接口权限
     *
     * @param userId
     * @return
     */
    List<String> findApiPathBatchByUserId(String userId);

    /**
     * 按照用户编号查询用户存在的菜单权限
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> findMenuBatchByUserId(String userId);

}
