package com.tcloud.component.token.config;

import cn.dev33.satoken.config.SaTokenConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * saToken 配置
 *
 * @author Anker
 */
@Configuration
public class SaTokenConfiguration {


    @Primary
    @Bean
    public SaTokenConfig saTokenConfig() {
        SaTokenConfig config = new SaTokenConfig();
        // token 名称（同时也是 cookie 名称）
        config.setTokenName("AccessToken");
        // token 有效期（单位：秒），默认30天，-1代表永不过期
        config.setTimeout(7 * 24 * 60 * 60);
        // token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结，默认-1 代表不限制，永不冻结
        config.setActiveTimeout(-1);
        // 是否允许同一账号多地同时登录（为 true 时允许一起登录，为 false 时新登录挤掉旧登录）
        config.setIsConcurrent(true);
        // 在多人登录同一账号时，是否共用一个 token （为 true 时所有登录共用一个 token，为 false 时每次登录新建一个 token）
        config.setIsShare(true);
        // token 风格
        config.setTokenStyle("random-128");
        // 是否输出操作日志
        config.setIsLog(false);
        // 不打印日志
        config.setIsPrint(false);
        // 打印彩色日志
        return config;
    }


}
