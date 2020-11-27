package com.icloud.common.feigns;

import com.icloud.common.utils.http.HttpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 42806
 */
@FeignClient(value = "i-cloud-rbac", url = "http://localhost:7005")
public interface FeignRbacProxy {

    /**
     * 按照用户编号获取用户接口权限路径
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/user_api_list")
    HttpResponse<List<String>> userApiList(@RequestParam String userId);

}
