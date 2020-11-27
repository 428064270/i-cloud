package com.icloud.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icloud.common.entitys.rbac.RbacUser;

import java.util.List;
import java.util.Map;

/**
 * 系统用户数据访问
 *
 * @author 42806
 */
public interface RbacUserMapper extends BaseMapper<RbacUser> {

    /**
     * 通过用户名称模糊查询
     *
     * @param name
     * @return
     */
    List<Map<String, Object>> selectListByName(String name);

}
