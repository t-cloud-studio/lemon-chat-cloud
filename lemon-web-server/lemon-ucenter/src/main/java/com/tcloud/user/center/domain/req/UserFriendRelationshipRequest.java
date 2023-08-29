package com.tcloud.user.center.domain.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserFriendRelationshipRequest implements Serializable {

    /**
     * 还有名称
     */
    private String fName;


}
