package com.icloud.rbac.controller;

import com.icloud.common.components.annotation.Permission;
import com.icloud.common.components.base.BaseController;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacResource;
import com.icloud.common.utils.http.HttpResponse;
import com.icloud.rbac.service.RbacResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统资源请求处理
 *
 * @author 42806
 */
@RestController
@RequestMapping("/resource")
public class RbacResourceController extends BaseController {

    private final RbacResourceService rbacResourceService;

    public RbacResourceController(RbacResourceService rbacResourceService) {
        super("RbacResourceController");
        this.rbacResourceService = rbacResourceService;
    }

    /**
     * 添加资源
     *
     * @param resource
     * @param bindingResult
     * @return
     */
    @PostMapping("/insert")
    @Permission(description = "新增资源", operation = Permission.Operation.ADD)
    public HttpResponse insert(@RequestBody @Validated RbacResource resource, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return this.rbacResourceService.insert(resource) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @Permission(description = "删除资源", operation = Permission.Operation.DELETE)
    public HttpResponse delete(@RequestParam String id) {
        if (StringUtils.isBlank(id)) {
            return HttpResponse.error("参数不完整，请联系管理员");
        }
        return this.rbacResourceService.deleteById(id) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 修改资源信息
     *
     * @param resource
     * @param bindingResult
     * @return
     */
    @PutMapping("/update")
    @Permission(description = "修改资源", operation = Permission.Operation.EDIT)
    public HttpResponse update(@RequestBody @Validated RbacResource resource, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.error(bindingResult.getFieldError().getDefaultMessage());
        }
        if (StringUtils.isBlank(resource.getId())) {
            return HttpResponse.error("参数不完整，请联系管理员");
        }
        return this.rbacResourceService.updateById(resource) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 查询树状资源列表
     *
     * @param param
     * @return
     */
    @PostMapping("/list")
    @Permission(description = "树状资源列表", operation = Permission.Operation.SELECT)
    public HttpResponse list(@RequestBody QueryPageParam param) {
        List<RbacResource> list = this.rbacResourceService.findToManyBatchByName(param);
        return HttpResponse.success(list);
    }


    /**
     * 查询菜单资源树结构数据
     *
     * @return
     */
    @GetMapping("/menu_options")
    @Permission(description = "二级菜单资源树结构", operation = Permission.Operation.SELECT)
    public HttpResponse menuOptions() {
        List<Map<String, Object>> list = this.rbacResourceService.selectMenuOptions();
        return HttpResponse.success(list);
    }

    /**
     * 查询菜单资源树结构数据
     *
     * @return
     */
    @GetMapping("/all_options")
    @Permission(description = "全部资源树结构", operation = Permission.Operation.SELECT)
    public HttpResponse allOptions() {
        List<Map<String, Object>> list = this.rbacResourceService.selectAllOptions();
        return HttpResponse.success(list);
    }

}
