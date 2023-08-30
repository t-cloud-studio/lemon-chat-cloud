package com.tcloud.component.token.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.tcloud.common.obj.vo.UserInfoVO;
import lombok.experimental.UtilityClass;

import static com.tcloud.component.token.constants.CoreConstant.WEB_TOKEN_ATTR_KEY;

@UtilityClass
public class TokenUtil {


    public Long getRequestUserId(){
        return StpUtil.getLoginIdAsLong();
    }


    public void setUserSessionAttr(UserInfoVO userInfoVO){
        StpUtil.getSession(true).set(WEB_TOKEN_ATTR_KEY, userInfoVO);
    }


    public UserInfoVO getUserInfo(){
        return (UserInfoVO) StpUtil.getSession().get(WEB_TOKEN_ATTR_KEY);
    }

}
