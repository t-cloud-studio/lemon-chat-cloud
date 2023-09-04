package com.tcloud.user.center.service.group;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloud.user.center.domain.entity.group.ChatGroup;
import com.tcloud.user.center.domain.req.ChatGroupCreateRequest;

public interface ChatGroupService extends IService<ChatGroup> {

    ChatGroup create(ChatGroupCreateRequest request, Long createUserId);
}
