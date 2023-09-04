package com.tcloud.user.center.mapper.contact;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloud.user.center.domain.entity.contact.UserContactRelationship;
import com.tcloud.user.center.domain.req.UserFriendRelationshipRequest;
import com.tcloud.user.center.domain.vo.UserContactPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface UserContactRelationshipMapper extends BaseMapper<UserContactRelationship> {
    Page<UserContactPageVO> contactList(Page<Object> toPage, UserFriendRelationshipRequest condition, Long requestUserId);

    Page<UserContactPageVO> selectToAddList(Page<Object> toPage, @Param("requestUserId") Long requestUserId);
}
