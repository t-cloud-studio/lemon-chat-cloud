package com.tcloud.web.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
@TableName("user_info")
public class UserInfo {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String cipher;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别：-1未知，0女，1男
     */
    private Integer gender;

    /**
     * 状态：0正常，1禁用
     */
    private Integer status;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
