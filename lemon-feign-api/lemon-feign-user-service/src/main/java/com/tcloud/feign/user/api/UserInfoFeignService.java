package com.tcloud.feign.user.api;

import com.tcloud.feign.user.fallback.UserInfoFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/feign/ucenter/")
@FeignClient(value = "lemon-user", contextId = "UserInfoFeignService", fallback = UserInfoFeignServiceFallback.class)
public interface UserInfoFeignService {


    @GetMapping("/user_exists/{phone}")
    boolean exists(@PathVariable("phone") String phone);



}
