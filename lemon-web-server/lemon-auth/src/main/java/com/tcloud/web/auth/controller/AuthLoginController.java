package com.tcloud.web.auth.controller;

import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.web.auth.domain.vo.LoginResponse;
import com.tcloud.web.auth.service.LoginService;
import com.tcloud.web.common.r.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证登录前端控制器
 *
 * @author 杨斌
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/app/user_info")
public class AuthLoginController {
    private final LoginService loginService;

    /**
     * 用户登录
     *
     * @param request 请求参数
     * @return {@link R<LoginResponse>}
     */
    @PostMapping("login_action")
    public R<LoginResponse> login(@RequestBody @Validated LoginRequest request){
        return R.data(loginService.login(request));
    }


}
