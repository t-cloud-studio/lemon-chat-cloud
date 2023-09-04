package com.tcloud.component.token.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.tcloud.common.obj.vo.UserInfoVO;
import lombok.experimental.UtilityClass;

import static com.tcloud.component.token.constants.CoreConstant.WEB_TOKEN_ATTR_KEY;

@UtilityClass
public class TokenUtil {

    /**
     * get current request user id
     *
     * @return {@link Long} current user id
     */
    public Long getRequestUserId(){
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * set user session attribute
     *
     * @param userInfoVO    user info view object
     */
    public void setUserSessionAttr(UserInfoVO userInfoVO){
        StpUtil.getSession(true).set(WEB_TOKEN_ATTR_KEY, userInfoVO);
    }

    /**
     * get login user info view object
     *
     * @return {@link UserInfoVO}
     */
    public UserInfoVO getUserInfo(){
        return (UserInfoVO) StpUtil.getSession().get(WEB_TOKEN_ATTR_KEY);
    }

    /**
     * login and set user session attribute
     *
     * @param id            login user id
     * @param userInfoVO    login user view object
     */
    public void loginAndSetUserSession(Long id, UserInfoVO userInfoVO) {
        StpUtil.login(id);
        setUserSessionAttr(userInfoVO);
    }
}
