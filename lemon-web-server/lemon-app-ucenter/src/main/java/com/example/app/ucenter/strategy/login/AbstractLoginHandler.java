package com.example.app.ucenter.strategy.login;


import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.example.app.ucenter.domain.entity.UserInfo;
import com.example.app.ucenter.domain.request.login.BaseLoginRequest;
import com.example.app.ucenter.domain.vo.LoginUser;
import com.tcloud.im.client.instance.LemonClient;
import com.tcloud.im.client.managers.impl.LemonClientManager;
import com.tcloud.im.lsb.balance.ServerLoadBalance;
import com.tcloud.im.lsb.config.LoadBalanceHandlerConfiguration;
import com.tcloud.register.domain.core.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractLoginHandler {

    @Autowired
    private ServerLoadBalance loadBalance;
    @Autowired
    private LemonClientManager lemonClientManager;
    /**
     * 账户验证
     *
     * @return 验证结果 SUCCESS 验证通过， 否则为错误提示
     */
    public abstract LoginUser doLoginAction(BaseLoginRequest loginRequest);



    protected void loginSuccess(UserInfo userInfo){
        StpUtil.login(userInfo.getId());
        //负载
        Server balance = loadBalance.balance();
        lemonClientManager.connect(balance.getIp(),balance.getPort());
        // 记录日志
        this.loginLog();
    }


    protected void loginFail(){

    }



    protected void loginLog(){

    }







}
