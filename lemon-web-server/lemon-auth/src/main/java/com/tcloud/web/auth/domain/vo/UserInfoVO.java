package com.tcloud.web.auth.domain.vo;

import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
public class UserInfoVO {

    /**
     * 主键
     */
    private Long id;
    /**
     * 头像
     */
    private String avatar;
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
     * 登录ip地址
     */
    private String loginIp;
    /**
     * 登录介质
     */
    private Platform platform;
    /**
     * 个人介绍
     */
    private String personalProfile;
}
