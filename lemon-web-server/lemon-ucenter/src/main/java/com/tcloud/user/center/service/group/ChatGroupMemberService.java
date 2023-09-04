package com.tcloud.user.center.service.group;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloud.user.center.domain.entity.group.ChatGroupMember;

import java.util.List;

public interface ChatGroupMemberService extends IService<ChatGroupMember> {
    int batchInsert(List<ChatGroupMember> memberList, Long groupId);
}
