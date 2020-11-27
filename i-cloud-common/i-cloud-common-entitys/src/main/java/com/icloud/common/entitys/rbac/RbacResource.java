package com.icloud.common.entitys.rbac;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 系统资源
 *
 * @author 42806
 */
@Data
@TableName("t_view_rbac_resource")
public class RbacResource implements Serializable {

    private String id;

    @TableField("`name`")
    @NotEmpty(message = "资源名称不能为空")
    @NotNull(message = "资源名称不能为空")
    private String name;

    @TableField("`type`")
    @NotNull(message = "资源类型不能为空")
    private Integer type;

    @NotEmpty(message = "访问路径不能为空")
    @NotNull(message = "访问路径不能为空")
    private String path;

    @TableField("parent_id")
    private String parentId;

    private Integer display = 0;

    @TableField("`order`")
    private Integer order = 0;

    private String icon;

    @TableField("component_path")
    private String componentPath;

    @TableField(exist = false)
    @NotNull(message = "父级资源不能为空")
    private List<String> optionValues;

    @TableField("option_values_json")
    private String optionValuesJson;

    @TableField(exist = false)
    private List<RbacResource> children;

    @Override
    public String toString() {
        return "RbacResource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", path='" + path + '\'' +
                ", parentId='" + parentId + '\'' +
                ", display=" + display +
                ", order=" + order +
                ", icon='" + icon + '\'' +
                ", componentPath='" + componentPath + '\'' +
                ", optionValues=" + optionValues +
                ", optionValuesJson='" + optionValuesJson + '\'' +
                '}';
    }
}
