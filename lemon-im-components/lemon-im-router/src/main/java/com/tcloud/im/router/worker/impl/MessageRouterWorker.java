package com.tcloud.im.router.worker.impl;

import com.tcloud.im.protocol.msg.LemonMessage;
import com.tcloud.im.router.worker.RouterWorker;
import com.tcloud.register.domain.ClientRouteServerInfo;
import com.tcloud.register.manager.client.ClientRegisterRelateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MessageRouterWorker extends RouterWorker {


    private final ClientRegisterRelateManager sessionManager;

    @Override
    public boolean route(Long userId, LemonMessage message) {
        ClientRouteServerInfo session = sessionManager.find(userId);
        if (Objects.nonNull(session)){
            throw new NullPointerException("client register info is null!!");
        }
        // TODO TCP消息转发

        return false;
    }
}
