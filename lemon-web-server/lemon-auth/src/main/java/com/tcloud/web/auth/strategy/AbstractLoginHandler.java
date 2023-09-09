package com.tcloud.web.auth.strategy;


import cn.hutool.core.date.DateUtil;
import com.tcloud.common.obj.vo.UserInfoVO;
import com.tcloud.component.token.utils.TokenUtil;
import com.tcloud.im.lsb.balance.ServerLoadBalance;
import com.tcloud.register.domain.ServerInfo;
import com.tcloud.web.auth.domain.entity.UserLoginLog;
import com.tcloud.web.auth.domain.request.LoginRequest;
import com.tcloud.web.auth.domain.vo.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 登录处理
 *
 * @author evans
 */
@Slf4j
public abstract class AbstractLoginHandler {


    @Autowired
    private ServerLoadBalance serverLoadBalance;

    /**
     * 账户验证
     *
     * @param loginRequest 请求参数
     * @return 验证结果 SUCCESS 验证通过， 否则为错误提示
     */
    public abstract UserInfoVO accountCheckout(LoginRequest loginRequest);


    public LoginResponse loginAction(LoginRequest loginRequest) {
        UserInfoVO userInfoVO = accountCheckout(loginRequest);
        this.loginSuccess(userInfoVO);
        return new LoginResponse.Builder()
                .userInfoVO(userInfoVO)
                .token(new LoginResponse.TokenInfo(TokenUtil.getTokenValue(), TokenUtil.getTokenName()))
                .routeInfo(this.allocateHealthyServerGate())
                .build();
    }

    /**
     * 分配健康的服务网关
     *
     * @return {@link LoginResponse.ServerRouteInfo}
     */
    private LoginResponse.ServerRouteInfo allocateHealthyServerGate() {
        ServerInfo balanceServer = serverLoadBalance.balance();
        return new LoginResponse.ServerRouteInfo(balanceServer.getIp(), balanceServer.getPort(), balanceServer.getWsPath());
    }


    protected void loginSuccess(UserInfoVO userInfoVO) {
        TokenUtil.loginAndSetUserSession(userInfoVO.getId(), userInfoVO);
        // 记录日志
        UserLoginLog loginLog = new UserLoginLog();
        loginLog.setLoginIp(userInfoVO.getLoginIp());
        loginLog.setLoginTime(DateUtil.date());
        loginLog.setStatus(true);
        loginLog.setUserId(userInfoVO.getId());
        log.info("{} 登录成功 >>> :{}", userInfoVO.getNickname(), loginLog);
    }


    protected void loginFail() {

    }


}
