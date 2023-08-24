package com.tcloud.web.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户登录日志实体类
 */
@Data
@TableName("user_login_log")
public class UserLoginLog {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 状态（0：成功，1：失败）
     */
    private boolean status;

    /**
     * 创建时间
     */
    private Date createdAt;
}
