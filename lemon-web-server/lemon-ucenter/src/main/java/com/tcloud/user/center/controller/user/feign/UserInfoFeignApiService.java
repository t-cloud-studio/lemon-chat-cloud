package com.tcloud.user.center.controller.user.feign;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloud.user.center.domain.entity.user.UserInfo;
import com.tcloud.user.center.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/ucenter")
public class UserInfoFeignApiService {


    @Autowired
    private UserInfoService userInfoService;


    @GetMapping("/user_exists/{phone}")
    public boolean exist(@PathVariable("phone") String phone){
        return userInfoService.count(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getPhone, phone)) > 0;
    }


}
