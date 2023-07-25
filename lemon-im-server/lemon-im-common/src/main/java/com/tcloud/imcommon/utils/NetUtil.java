package com.tcloud.imcommon.utils;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

@Slf4j
@UtilityClass
public class NetUtil {

    public String getHostAddress() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            log.error("obtain the ip address failed:", ex);
        }
        return ip;
    }

}
