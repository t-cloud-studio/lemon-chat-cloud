package com.tcloud.web.auth.service;

import com.tcloud.web.auth.domain.request.login.BaseLoginRequest;
import com.tcloud.web.auth.domain.vo.LoginUser;

public interface LoginService {



    LoginUser login(BaseLoginRequest loginRequest);



}
