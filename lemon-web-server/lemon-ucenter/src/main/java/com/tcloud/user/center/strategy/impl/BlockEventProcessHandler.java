package com.tcloud.user.center.strategy.impl;

import com.tcloud.user.center.domain.entity.contact.UserContactRelationship;
import com.tcloud.user.center.mapper.contact.UserContactRelationshipMapper;
import com.tcloud.user.center.strategy.IAgreeEventProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockEventProcessHandler implements IAgreeEventProgress {

    @Autowired
    private UserContactRelationshipMapper userContactRelationshipMapper;

    @Override
    public void eventProgress(UserContactRelationship relationship) {
        //TODO 记一条黑名单
        boolean success = this.blockRecord(relationship);
        if (success){
            // 删除relation记录
            userContactRelationshipMapper.deleteById(relationship.getId());
        }
    }

    private boolean blockRecord(UserContactRelationship relationship) {
        return false;
    }
}
