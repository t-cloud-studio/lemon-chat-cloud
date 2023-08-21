package com.tcloud.app.ucenter.controller;

import com.tcloud.app.ucenter.domain.request.login.BaseLoginRequest;
import com.tcloud.app.ucenter.domain.vo.LoginUser;
import com.tcloud.app.ucenter.service.LoginService;
import com.tcloud.web.common.r.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web/app/user_info")
public class UserLoginController {
    private final LoginService loginService;


    @PostMapping("login_action")
    public R<LoginUser> login(@RequestBody BaseLoginRequest request){
        return R.data(loginService.login(request));
    }


}
