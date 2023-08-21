package com.tcloud.app.ucenter.service;

import com.tcloud.app.ucenter.domain.request.login.BaseLoginRequest;
import com.tcloud.app.ucenter.domain.vo.LoginUser;

public interface LoginService {



    LoginUser login(BaseLoginRequest loginRequest);



}
