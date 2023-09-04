package com.tcloud.user.center.service.contact.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloud.component.token.utils.TokenUtil;
import com.tcloud.user.center.domain.entity.contact.UserContactRelationship;
import com.tcloud.user.center.domain.entity.user.UserInfo;
import com.tcloud.user.center.domain.req.UserAddConcatRequest;
import com.tcloud.user.center.domain.req.UserAgreeEventRequest;
import com.tcloud.user.center.domain.req.UserFriendRelationshipRequest;
import com.tcloud.user.center.domain.vo.UserContactPageVO;
import com.tcloud.user.center.enums.ContactAgreeStatus;
import com.tcloud.user.center.mapper.contact.UserContactRelationshipMapper;
import com.tcloud.user.center.service.contact.ContactRelationshipService;
import com.tcloud.user.center.service.user.UserInfoService;
import com.tcloud.user.center.strategy.IAgreeEventProgress;
import com.tcloud.web.common.constants.NumConstant;
import com.tcloud.web.common.domain.PageRequest;
import com.tcloud.web.common.enums.BoolEnum;
import com.tcloud.web.common.exceptions.ApplicationBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ContactRelationshipServiceImpl extends ServiceImpl<UserContactRelationshipMapper, UserContactRelationship> implements ContactRelationshipService {


    @Autowired
    private UserInfoService userInfoService;


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
    public void doAgreeEvent(UserAgreeEventRequest request, Long requestUserId) {
        UserContactRelationship relationship = baseMapper.selectById(request.getRelationId());
        Assert.isTrue(Objects.nonNull(relationship), () -> new ApplicationBizException("好友申请已失效"));
        Assert.isTrue(relationship.getContactUserId().equals(requestUserId), () -> new ApplicationBizException("无效的好友申请"));


        IAgreeEventProgress handlerBean = Optional.ofNullable(SpringUtil.getBean(request.getAgreeStatus().getHandler())).orElseThrow(() -> new ApplicationBizException("不被允许的操作类型"));

        handlerBean.eventProgress(relationship);
    }


    @Override
    public boolean hasRelation(Long createUserId, Integer userId) {
        Long count = baseMapper.selectCount(Wrappers.<UserContactRelationship>lambdaQuery()
                .eq(UserContactRelationship::getUserId, createUserId)
                .eq(UserContactRelationship::getContactUserId, userId)
                .eq(UserContactRelationship::getAgreed, ContactAgreeStatus.AGREED));
        return count.compareTo(NumConstant.ZERO) > 0;
    }
}
