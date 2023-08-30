package com.tcloud.user.center.service.contact.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloud.component.token.utils.TokenUtil;
import com.tcloud.user.center.domain.entity.contact.UserContactRelationship;
import com.tcloud.user.center.domain.entity.user.UserInfo;
import com.tcloud.user.center.domain.req.UserAddConcatRequest;
import com.tcloud.user.center.domain.req.UserFriendRelationshipRequest;
import com.tcloud.user.center.domain.vo.UserContactPageVO;
import com.tcloud.user.center.enums.ContactAgreeStatus;
import com.tcloud.user.center.mapper.contact.UserContactRelationshipMapper;
import com.tcloud.user.center.service.contact.IUserContactRelationshipService;
import com.tcloud.user.center.service.user.IUserInfoService;
import com.tcloud.web.common.constants.NumConstant;
import com.tcloud.web.common.domain.PageRequest;
import com.tcloud.web.common.enums.BoolEnum;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import jakarta.validation.constraints.AssertTrue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContactRelationshipServiceImpl extends ServiceImpl<UserContactRelationshipMapper, UserContactRelationship> implements IUserContactRelationshipService {


    private final IUserInfoService userInfoService;


    @Override
    public Page<UserContactPageVO> contactList(PageRequest<UserFriendRelationshipRequest> request) {
        return baseMapper.contactList(request.toPage(), request.getCondition(UserFriendRelationshipRequest.class), TokenUtil.getRequestUserId());
    }


    @Override
    public void addContact(UserAddConcatRequest request, Long requestUserId) {
        UserInfo contactUser = Optional.ofNullable(userInfoService.getById(request.getTargetUserId())).orElseThrow(() -> new ApplicationBizException("用户不存在"));
        Assert.isTrue(BoolEnum.isFalse(contactUser.getDeleted()), () -> new ApplicationBizException("用户不存在"));
        // TODO 好友上限校验
        Long currentContactCount = this.baseMapper.selectCount(Wrappers.<UserContactRelationship>lambdaQuery()
                .eq(UserContactRelationship::getUserId, requestUserId)
                .eq(UserContactRelationship::getDeleted, BoolEnum.FALSE.getRes()));
        Assert.isTrue(currentContactCount.compareTo(NumConstant.FIVE_HUNDRED) <= 0, () -> new ApplicationBizException("超出好友上限"));

        UserContactRelationship relationship = UserContactRelationship.build(contactUser.getId(), requestUserId);
        save(relationship);
    }


    @Override
    public Page<UserContactPageVO> userToAddList(PageRequest<?> request, Long requestUserId) {
        return baseMapper.selectToAddList(request.toPage(), requestUserId);
    }

    @Override
    public UserContactPageVO getToAddContactInfo(Long id, Long requestUserId) {
        return null;
    }

    @Override
    public void doAgreeEvent(Long relationId, Long requestUserId) {
        UserContactRelationship relationship = baseMapper.selectById(relationId);
        Assert.isTrue(ContactAgreeStatus.isAgreed(relationship.getAgreed()), () -> new ApplicationBizException("对方已同意"));
        Assert.isFalse(ContactAgreeStatus.isBlock(relationship.getAgreed()), () -> new ApplicationBizException("对方已将您拉入黑名单"));
        Assert.isTrue(ContactAgreeStatus.disagreed(relationship.getAgreed()), () -> new ApplicationBizException("对方拒绝你的好友申请"));

        UserContactRelationship newRelation = UserContactRelationship.build(relationship.getUserId(), requestUserId);
        newRelation.setAgreed(ContactAgreeStatus.AGREED.getStatus());

        save(newRelation);



    }
}
