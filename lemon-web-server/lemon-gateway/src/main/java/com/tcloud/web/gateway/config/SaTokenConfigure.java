package com.tcloud.web.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author click33
 */
@Configuration
public class SaTokenConfigure {


    @Autowired
    private ExcludePatternConfig excludePatternConfig;


    // 注册 Sa-Token全局过滤器 
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截全部path
                .addInclude("/**")
                // 配置的放开地址
                .setExcludeList(excludePatternConfig.getPatterns())
                // 默认放开地址
                .addExclude("/favicon.ico")
                // 放开全部feign调用地址
                .addExclude("/feign/*")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**", "/user/doLogin", r -> StpUtil.checkLogin());

                    // 权限认证 -- 不同模块, 校验不同权限
                    SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
                    SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
                    SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
                    SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
                    // 更多匹配 ...  */
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> {
                    return SaResult.error(e.getMessage());
                });
    }
}
