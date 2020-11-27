package com.icloud.security.auth;

import com.icloud.common.feigns.FeignRbacProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 42806
 */
@SpringBootApplication(scanBasePackages = {"com.icloud.common.components", "com.icloud.security.auth"})
@MapperScan("com.icloud.security.auth.mapper")
@EnableEurekaClient
@EnableFeignClients(clients = {FeignRbacProxy.class})
public class SecurityAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAuthApplication.class, args);
    }

}
