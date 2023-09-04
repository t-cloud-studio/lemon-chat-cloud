package com.tcloud.web.auth.service;


import com.tcloud.web.auth.domain.request.RegisterRequest;


public interface UserRegisterService {


    /**
     * 注册
     */
    void Register(RegisterRequest request);

}
