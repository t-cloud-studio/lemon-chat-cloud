package com.tcloud.web.auth.service;


import com.tcloud.web.auth.domain.request.RegisterRequest;


/**
 * @author evans
 */
public interface UserRegisterService {


    /**
     * 注册
     *
     * @param request 注册请求
     */
    void registerAccount(RegisterRequest request);

}
