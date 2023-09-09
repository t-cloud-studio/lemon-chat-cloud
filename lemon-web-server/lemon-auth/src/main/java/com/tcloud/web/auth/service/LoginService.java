package com.tcloud.web.auth.service;

import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.web.auth.domain.vo.LoginResponse;

public interface LoginService {


    /**
     * 登录
     *
     * @param loginRequest  登录请求
     * @return              登录响应
     */
    LoginResponse login(LoginRequest loginRequest);



}
