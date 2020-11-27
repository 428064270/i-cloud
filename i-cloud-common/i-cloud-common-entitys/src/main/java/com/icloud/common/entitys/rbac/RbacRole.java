package com.icloud.common.entitys.rbac;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author 42806
 */
@Data
@TableName("t_view_rbac_role")
public class RbacRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message = "角色名称不能为空")
    @NotEmpty(message = "角色名称不能为空")
    private String name;

    @TableField(exist = false)
    @NotNull(message = "资源权限不能为空")
    private List<List<String>> optionValues;

    @TableField("option_values_json")
    private String optionValuesJson;

}
