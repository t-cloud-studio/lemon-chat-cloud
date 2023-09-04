package com.tcloud.user.center.service.group.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrPool;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloud.user.center.domain.entity.group.ChatGroup;
import com.tcloud.user.center.domain.entity.group.ChatGroupMember;
import com.tcloud.user.center.domain.entity.user.UserInfo;
import com.tcloud.user.center.domain.req.ChatGroupCreateRequest;
import com.tcloud.user.center.mapper.group.ChatGroupMapper;
import com.tcloud.user.center.service.contact.ContactRelationshipService;
import com.tcloud.user.center.service.group.ChatGroupMemberService;
import com.tcloud.user.center.service.group.ChatGroupService;
import com.tcloud.user.center.service.user.UserInfoService;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroup> implements ChatGroupService {


    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ChatGroupMemberService chatGroupMemberService;
    @Autowired
    private ContactRelationshipService userContactRelationshipService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChatGroup create(ChatGroupCreateRequest request, Long createUserId) {
        List<ChatGroupMember> memberList = this.checkAndBuild(request, createUserId);
        Assert.isTrue(CollUtil.isNotEmpty(memberList), () -> new ApplicationBizException("无效的群成员"));
        ChatGroup chatGroup = this.buildChantGroup(memberList, createUserId);
        boolean save = save(chatGroup);
        if(save){
            chatGroupMemberService.batchInsert(memberList, chatGroup.getId());
        }
        return chatGroup;
    }

    private ChatGroup buildChantGroup(List<ChatGroupMember> memberList, Long createUserId) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setGroupName(memberList.get(0).getNickname().concat(StrPool.COMMA).concat(StrPool.DOUBLE_DOT));
        chatGroup.setOwner(createUserId);
        chatGroup.setCreateUserId(createUserId);
        return chatGroup;
    }

    private List<ChatGroupMember> checkAndBuild(ChatGroupCreateRequest request, Long createUserId) {
        List<ChatGroupMember> memberList = Lists.newArrayListWithCapacity(500);
        for (Integer userId : request.getGroupMembers()) {
            UserInfo userInfo = userInfoService.findValidUserById(userId);
            if (Objects.isNull(userInfo)){
                continue;
            }
            boolean hasRelation = userContactRelationshipService.hasRelation(createUserId, userId);
            if (!hasRelation){
                continue;
            }
            memberList.add(new ChatGroupMember(userInfo.getId(), userInfo.getNickname(), userInfo.getAvatar()));
        }
        return memberList;
    }
}
