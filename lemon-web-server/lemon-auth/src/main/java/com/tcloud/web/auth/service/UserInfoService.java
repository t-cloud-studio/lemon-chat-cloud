package com.tcloud.web.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloud.web.auth.domain.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {
    /**
     * 通过Id查询有效用户
     *
     * @param userId 用户id
     * @return {@link UserInfo}
     */
    UserInfo findValidUserById(Integer userId);

    UserInfo existsPhone(String phone);

    /**
     * 创建并保存用户
     *
     * @param phone
     * @param cipher
     * @return
     */
    UserInfo createUser(String phone, String cipher);

    /**
     * 使用手机号查询用户
     *
     * @param phone 手机号码
     * @return {@link UserInfo}
     */
    UserInfo selectUserInfoByPhone(String phone);
}
