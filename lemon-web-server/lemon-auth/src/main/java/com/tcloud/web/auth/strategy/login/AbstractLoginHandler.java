package com.tcloud.web.auth.strategy.login;


import com.tcloud.web.auth.domain.entity.UserInfo;
import com.tcloud.web.auth.domain.request.login.BaseLoginRequest;
import com.tcloud.web.auth.domain.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractLoginHandler {


    /**
     * 账户验证
     *
     * @return 验证结果 SUCCESS 验证通过， 否则为错误提示
     */
    public abstract LoginUser doLoginAction(BaseLoginRequest loginRequest);



    protected void loginSuccess(UserInfo userInfo){
//        StpUtil.login(userInfo.getId());
//        //负载
//        Server balance = loadBalance.balance();
//        lemonClientManager.connect(balance.getIp(),balance.getPort());
        // 记录日志
        this.loginLog();
    }


    protected void loginFail(){

    }



    protected void loginLog(){

    }







}
