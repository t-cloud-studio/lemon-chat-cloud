package com.tcloud.user.center.service.group.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloud.user.center.domain.entity.group.ChatGroupMember;
import com.tcloud.user.center.mapper.group.ChatGroupMemberMapper;
import com.tcloud.user.center.service.group.ChatGroupMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatGroupMemberServiceImpl extends ServiceImpl<ChatGroupMemberMapper, ChatGroupMember> implements ChatGroupMemberService {

    @Override
    public int batchInsert(List<ChatGroupMember> memberList, Long groupId) {
        return baseMapper.batchInsert(memberList,groupId);
    }
}
