package com.tcloud.user.center.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChatGroupInfoVO {


    private Long groupId;

    private String groupName;

    private String describe;

    private List<GroupMemberInfoVO> members;



}
