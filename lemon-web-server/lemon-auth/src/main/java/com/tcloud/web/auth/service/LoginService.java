package com.tcloud.web.auth.service;

import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.web.auth.domain.vo.LoginResponse;

public interface LoginService {



    LoginResponse login(LoginRequest loginRequest);



}
