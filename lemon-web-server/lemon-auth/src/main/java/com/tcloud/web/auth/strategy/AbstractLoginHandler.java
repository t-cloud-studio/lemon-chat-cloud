package com.tcloud.web.auth.strategy;


;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.tcloud.component.token.utils.TokenUtil;
import com.tcloud.web.auth.domain.entity.UserLoginLog;
import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.web.auth.domain.vo.LoginResponse;
import com.tcloud.common.obj.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;


@Slf4j
public abstract class AbstractLoginHandler {


    /**
     * 账户验证
     *
     * @return 验证结果 SUCCESS 验证通过， 否则为错误提示
     */
    public abstract UserInfoVO accountCheckout(LoginRequest loginRequest);


    public LoginResponse loginAction(LoginRequest loginRequest){
        UserInfoVO userInfoVO = accountCheckout(loginRequest);
        if (Objects.isNull(userInfoVO)){
            loginFail();
        } else {
            loginSuccess(userInfoVO);
        }
        return new LoginResponse.Builder()
                .userInfoVO(userInfoVO)
                .token(new LoginResponse.TokenInfo(StpUtil.getTokenValue(), StpUtil.getTokenName()))
                .routeInfo(new LoginResponse.ServerRouteInfo("192.168.30.133", 9900))
                .build();
    }


    protected void loginSuccess(UserInfoVO userInfoVO){
        TokenUtil.loginAndSetUserSession(userInfoVO.getId(), userInfoVO);
        // 记录日志
        UserLoginLog loginLog = new UserLoginLog();
        loginLog.setLoginIp(userInfoVO.getLoginIp());
        loginLog.setLoginTime(DateUtil.date());
        loginLog.setStatus(true);
        loginLog.setUserId(userInfoVO.getId());
        log.info("{} 登录成功 >>> :{}", userInfoVO.getNickname(), loginLog);
    }


    protected void loginFail(){

    }








}
