package com.tcloud.web.auth.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class LoginResponse {

    /**
     * 用户信息
     */
    private UserInfoVO userInfo;
    /**
     * token信息
     */
    private TokenInfo token;
    /**
     * lsb 服务信息
     */
    private ServerRouteInfo routeInfo;


    private LoginResponse(Builder builder) {
        this.userInfo = builder.userInfoVO;
        this.token = builder.token;
        this.routeInfo = builder.routeInfo;
    }


    public static class Builder {
        private UserInfoVO userInfoVO;
        private TokenInfo token;

        private ServerRouteInfo routeInfo;

        public Builder userInfoVO(UserInfoVO userInfoVO) {
            this.userInfoVO = userInfoVO;
            return this;
        }

        public Builder token(TokenInfo token) {
            this.token = token;
            return this;
        }

        public Builder routeInfo(ServerRouteInfo routeInfo){
            this.routeInfo = routeInfo;
            return this;
        }

        public LoginResponse build() {
            return new LoginResponse(this);
        }
    }

    @Data
    @AllArgsConstructor
    public static class TokenInfo{
        /**
         * token值
         */
        private String token;
        /**
         * token名称
         */
        private String tokenName;
    }

    @Data
    @AllArgsConstructor
    public static class ServerRouteInfo{
        /**
         * 域名地址
         */
        private String dnsAddress;
        /**
         * 主机地址
         */
        private String host;
        /**
         * 主机端口
         */
        private Integer port;

        public ServerRouteInfo(String host, Integer port) {
            this.host = host;
            this.port = port;
        }
    }
}
