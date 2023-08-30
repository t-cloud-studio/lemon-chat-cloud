package com.tcloud.user.center.domain.entity.contact;

import com.baomidou.mybatisplus.annotation.*;
import com.tcloud.user.center.domain.entity.user.UserInfo;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_contact_relationship")
public class UserContactRelationship {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 联系人id
     */
    private Long contactUserId;
    /**
     * 备注名称
     */
    private String alias;
    /**
     * 其他备注
     */
    private String remark;
    /**
     * 是否同意
     */
    private Integer agreed;
    /**
     * 1 已删除
     */
    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    public static UserContactRelationship build(Long contactUserId, Long requestUserId) {
        UserContactRelationship relationship = new UserContactRelationship();
        relationship.setUserId(requestUserId);
        relationship.setContactUserId(contactUserId);
        return relationship;
    }
}
