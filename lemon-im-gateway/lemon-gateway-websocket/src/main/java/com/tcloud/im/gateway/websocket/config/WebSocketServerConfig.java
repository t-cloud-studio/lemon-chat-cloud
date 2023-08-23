package com.tcloud.im.gateway.websocket.config;

import com.tcloud.im.common.constants.CoreConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * WebSocket 服务网关配置
 *
 * @author evans
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "im.server")
public class WebSocketServerConfig implements InitializingBean {


    /**
     * start on port
     */
    private Integer port;

    private String wsPath;
    /**
     * 打印banner
     */
    private boolean printBanner = true;

    /**
     * the work group threads count , default value is the cpu count * 2
     */
    private Integer workGroupCount = Runtime.getRuntime().availableProcessors() * 2;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.isNull(port)) {
            log.warn("don`t hava port config, will use the default port:{}", CoreConstant.DEFAULT_PORT);
            this.port = CoreConstant.DEFAULT_PORT;
        }

    }
}
