package com.icloud.rbac.controller;

import com.icloud.common.components.annotation.Permission;
import com.icloud.common.components.base.BaseController;
import com.icloud.common.components.common.RedisComponent;
import com.icloud.common.entitys.common.PageResult;
import com.icloud.common.entitys.params.QueryPageParam;
import com.icloud.common.entitys.rbac.RbacResource;
import com.icloud.common.entitys.rbac.RbacUser;
import com.icloud.common.utils.http.HttpResponse;
import com.icloud.rbac.service.RbacResourceService;
import com.icloud.rbac.service.RbacUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户请求处理
 *
 * @author 42806
 */
@RestController
@RequestMapping("/user")
public class RbacUserController extends BaseController {

    private final RbacUserService rbacUserService;

    private final RbacResourceService rbacResourceService;

    private final RedisComponent redis;

    public RbacUserController(RbacUserService rbacUserService, RbacResourceService rbacResourceService, RedisComponent redis) {
        super("RbacUserController");
        this.rbacUserService = rbacUserService;
        this.rbacResourceService = rbacResourceService;
        this.redis = redis;
    }

    /**
     * 添加用户
     *
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("/insert")
    @Permission(description = "新增用户", operation = Permission.Operation.ADD)
    public HttpResponse insert(@RequestBody @Validated RbacUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.error(bindingResult.getFieldError().getDefaultMessage());
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return HttpResponse.error("密码不能为空");
        }
        return this.rbacUserService.insert(user) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @Permission(description = "删除用户", operation = Permission.Operation.DELETE)
    public HttpResponse delete(@RequestParam String id) {
        if (StringUtils.isBlank(id)) {
            return HttpResponse.error("参数不完整，请联系管理员");
        }
        return this.rbacUserService.deleteById(id) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @param bindingResult
     * @return
     */
    @PutMapping("/update")
    @Permission(description = "修改用户", operation = Permission.Operation.EDIT)
    public HttpResponse update(@RequestBody @Validated RbacUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.error(bindingResult.getFieldError().getDefaultMessage());
        }
        if (StringUtils.isBlank(user.getId())) {
            return HttpResponse.error("参数不完整，请联系管理员");
        }
        return this.rbacUserService.updateById(user) ? HttpResponse.success() : HttpResponse.error("系统发生内部错误，请联系管理员");
    }

    /**
     * 查询用户列表
     *
     * @param param
     * @return
     */
    @PostMapping("/list")
    @Permission(description = "用户列表", operation = Permission.Operation.SELECT)
    public HttpResponse list(@RequestBody QueryPageParam param) {
        PageResult<Map<String, Object>> RbacUserPageResult = this.rbacUserService.findListPage(param);
        return HttpResponse.success(RbacUserPageResult);
    }

    /**
     * 通过用户编号查询用户存在的接口权限
     *
     * @param userId
     * @return
     */
    @GetMapping("/user_api_list")
    public HttpResponse<List<String>> userApiList(@RequestParam String userId) {
        List<String> list = this.rbacResourceService.findApiPathBatchByUserId(userId);
        return HttpResponse.success(list);
    }

    /**
     * 通过Token获取用户信息
     */
    @GetMapping("/info")
    @Permission(description = "获取用户信息", operation = Permission.Operation.SELECT)
    public HttpResponse<?> code(@RequestParam String token) {
        //从Redis中获取用户信息
        RbacUser user = (RbacUser) this.redis.get(token);
        if (user == null) {
            return HttpResponse.error(50008, "验证信息已过期");
        }
        user.setPassword("");
        //按照用户编号获取出用户存在的菜单列表
        List<RbacResource> menus = this.rbacResourceService.findMenuListByUserId(user.getId());
        user.setMenus(menus);
        return HttpResponse.success(user);
    }

}
