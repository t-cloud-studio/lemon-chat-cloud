package com.tcloud.user.center.strategy.impl;

import com.tcloud.user.center.domain.entity.contact.UserContactRelationship;
import com.tcloud.user.center.enums.ContactAgreeStatus;
import com.tcloud.user.center.mapper.contact.UserContactRelationshipMapper;
import com.tcloud.user.center.strategy.IAgreeEventProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拒绝申请事件处理
 *
 * @author Anker
 */
@Service
@RequiredArgsConstructor
public class DisagreeEventProcessHandler implements IAgreeEventProgress {

    @Autowired
    private UserContactRelationshipMapper userContactRelationshipMapper;

    @Override
    public void eventProgress(UserContactRelationship relationship) {
        relationship.setAgreed(ContactAgreeStatus.DISAGREE.getStatus());
        userContactRelationshipMapper.updateById(relationship);
    }
}
