package com.tcloud.user.center.mapper.group;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloud.user.center.domain.entity.group.ChatGroupMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatGroupMemberMapper extends BaseMapper<ChatGroupMember> {
    int batchInsert(@Param("members") List<ChatGroupMember> memberList, @Param("groupId") Long groupId);
}
