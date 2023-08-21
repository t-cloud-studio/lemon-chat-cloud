package com.tcloud.register.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRouteServerInfo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 服务ip地址
     */
    private String ip;
    /**
     * 服务端口信息
     */
    private int port;

}
