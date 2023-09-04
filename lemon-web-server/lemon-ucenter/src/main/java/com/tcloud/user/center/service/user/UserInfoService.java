package com.tcloud.user.center.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloud.user.center.domain.entity.user.UserInfo;

public interface UserInfoService extends IService<UserInfo> {
    /**
     * 通过Id查询有效用户
     *
     * @param userId 用户id
     * @return {@link UserInfo}
     */
    UserInfo findValidUserById(Integer userId);

}
