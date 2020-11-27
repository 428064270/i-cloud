package com.icloud.rbac.controller;

import com.icloud.common.components.annotation.Permission;
import com.icloud.common.components.base.BaseController;
import com.icloud.common.entitys.common.PageResult;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacRole;
import com.icloud.common.utils.http.HttpResponse;
import com.icloud.rbac.service.RbacRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色请求处理
 *
 * @author 42806
 */
@RestController
@RequestMapping("/role")
public class RbacRoleController extends BaseController {

    private final RbacRoleService rbacRoleService;

    public RbacRoleController(RbacRoleService rbacRoleService) {
        super("RbacRoleController");
        this.rbacRoleService = rbacRoleService;
    }

    /**
     * 添加角色
     *
     * @param role
     * @param bindingResult
     * @return
     */
    @PostMapping("/insert")
    @Permission(description = "新增角色", operation = Permission.Operation.ADD)
    public HttpResponse insert(@RequestBody @Validated RbacRole role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return this.rbacRoleService.insert(role) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @Permission(description = "删除角色", operation = Permission.Operation.DELETE)
    public HttpResponse delete(@RequestParam String id) {
        if (StringUtils.isBlank(id)) {
            return HttpResponse.error("参数不完整，请联系管理员");
        }
        return this.rbacRoleService.deleteById(id) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @param bindingResult
     * @return
     */
    @PutMapping("/update")
    @Permission(description = "修改角色", operation = Permission.Operation.EDIT)
    public HttpResponse update(@RequestBody @Validated RbacRole role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.error(bindingResult.getFieldError().getDefaultMessage());
        }
        if (StringUtils.isBlank(role.getId())) {
            return HttpResponse.error("参数不完整，请联系管理员");
        }
        return this.rbacRoleService.updateById(role) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 查询角色列表
     *
     * @param param
     * @return
     */
    @PostMapping("/list")
    @Permission(description = "角色列表", operation = Permission.Operation.SELECT)
    public HttpResponse list(@RequestBody QueryPageParam param) {
        PageResult<RbacRole> rbacRolePageResult = this.rbacRoleService.findListPage(param);
        return HttpResponse.success(rbacRolePageResult);
    }

    /**
     * 查询全部角色
     *
     * @return
     */
    @GetMapping("/all_options")
    @Permission(description = "全部角色-下拉框", operation = Permission.Operation.SELECT)
    public HttpResponse allOptions() {
        List<RbacRole> list = this.rbacRoleService.selectOptions();
        return HttpResponse.success(list);
    }

}
