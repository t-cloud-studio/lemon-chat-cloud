package com.tcloud.register.domain.core;

import com.tcloud.register.domain.pojo.UserInfo;
import lombok.Data;

@Data
public class ClientSession {


    private String sessionId;


    private UserInfo userInfo;


    private Server server;


}
