package com.tcloud.feign.user.api;

import com.tcloud.feign.user.fallback.UserInfoFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "lemon-user", contextId = "UserInfoFeignService", fallback = UserInfoFeignServiceFallback.class)
public interface UserInfoFeignService {





}
