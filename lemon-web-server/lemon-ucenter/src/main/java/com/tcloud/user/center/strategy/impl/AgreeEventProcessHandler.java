package com.tcloud.user.center.strategy.impl;

import com.tcloud.user.center.domain.entity.contact.UserContactRelationship;
import com.tcloud.user.center.enums.ContactAgreeStatus;
import com.tcloud.user.center.mapper.contact.UserContactRelationshipMapper;
import com.tcloud.user.center.strategy.IAgreeEventProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户同意好友申请
 *
 * @author Anker
 */
@Service
public class AgreeEventProcessHandler implements IAgreeEventProgress {


    @Autowired
    private UserContactRelationshipMapper userContactRelationshipMapper;

    @Override
    public void eventProgress(UserContactRelationship relationship) {
        UserContactRelationship newRelationship = UserContactRelationship.build(relationship.getUserId(), relationship.getContactUserId());
        newRelationship.setAgreed(ContactAgreeStatus.AGREED.getStatus());
        userContactRelationshipMapper.insert(newRelationship);
    }
}
