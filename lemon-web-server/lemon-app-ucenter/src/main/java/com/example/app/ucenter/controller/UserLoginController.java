package com.example.app.ucenter.controller;

import com.example.app.ucenter.domain.request.login.BaseLoginRequest;
import com.example.app.ucenter.domain.vo.LoginUser;
import com.example.app.ucenter.service.LoginService;
import com.tcloud.web.common.r.R;
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
