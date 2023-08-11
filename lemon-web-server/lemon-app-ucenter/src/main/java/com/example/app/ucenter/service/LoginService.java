package com.example.app.ucenter.service;

import com.example.app.ucenter.domain.request.login.BaseLoginRequest;
import com.example.app.ucenter.domain.vo.LoginUser;

public interface LoginService {



    LoginUser login(BaseLoginRequest loginRequest);



}
