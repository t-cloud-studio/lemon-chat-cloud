package com.tcloud.register.domain.pojo;

import com.tcloud.im.common.enums.DeviceType;
import lombok.Data;

@Data
public class UserInfo {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 登录设备类型
     */
    private DeviceType deviceType;

}
