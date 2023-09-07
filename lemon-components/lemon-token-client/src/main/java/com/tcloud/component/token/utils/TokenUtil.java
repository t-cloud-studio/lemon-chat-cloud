package com.tcloud.component.token.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.tcloud.common.obj.vo.UserInfoVO;
import lombok.experimental.UtilityClass;

import java.util.Objects;

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

    public static String getTokenName() {
        return StpUtil.getTokenName();
    }

    /**
     * 使用token获取登录用户id
     *
     * @param token token字符串
     * @return {@link  Long} userId
     */
    public static Long getUserIdByToken(String token) {
        // token
        if (CharSequenceUtil.isBlank(token)) {
            return null;
        }
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        if (Objects.isNull(loginIdByToken)){
            return null;
        }
        return Long.parseLong(loginIdByToken.toString());
    }



    public static UserInfoVO getLongUserVOById(Long userId) {
        SaSession session = StpUtil.getSessionByLoginId(userId);
        return (UserInfoVO)session.get(WEB_TOKEN_ATTR_KEY);
    }

    public static boolean isLogin(String token) {
        return StpUtil.isLogin(getUserIdByToken(token));
    }
}
