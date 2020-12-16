package com.icloud.common.entitys.rbac;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 42806
 */
@Data
@TableName("t_view_rbac_user")
public class RbacUser implements Serializable {

    private static final long serialVersionUID = 7702131510753456113L;

    private String id;

    @NotNull(message = "用户名不能为空")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    private String password;

    @NotNull(message = "姓名不能为空")
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @TableField(exist = false)
    @NotNull(message = "角色不能为空")
    private List<String> optionValues;

    @TableField("option_values_json")
    private String optionValuesJson;

    @TableField(exist = false)
    private List<RbacResource> menus;

}
