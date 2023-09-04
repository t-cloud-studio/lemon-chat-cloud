package com.tcloud.feign.user.fallback;

import com.tcloud.feign.user.api.UserInfoFeignService;

public class UserInfoFeignServiceFallback implements UserInfoFeignService {

    @Override
    public boolean exists(String phone) {
        return false;
    }
}
