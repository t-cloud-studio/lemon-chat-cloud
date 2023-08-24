package com.tcloud.web.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 配置放行路径
 *
 * @author Anker
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.gateway.exclude")
public class ExcludePatternConfig {


    /**
     * 放行路径
     */
    private List<String> patterns;



}
