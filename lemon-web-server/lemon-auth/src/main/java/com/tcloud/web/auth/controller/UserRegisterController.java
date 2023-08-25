package com.tcloud.web.auth.controller;

import com.tcloud.web.auth.domain.request.RegisterRequest;
import com.tcloud.web.auth.service.IUserRegisterService;
import com.tcloud.web.common.r.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册
 *
 * @author Anker
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/auth/user_info")
public class UserRegisterController {

    private final IUserRegisterService userRegisterService;


    @PostMapping("user_register")
    public R<Void> userRegister(RegisterRequest request){
        userRegisterService.Register(request);
        return R.success();
    }

}
