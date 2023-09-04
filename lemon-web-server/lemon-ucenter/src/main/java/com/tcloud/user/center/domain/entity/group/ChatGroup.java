package com.tcloud.user.center.domain.entity.group;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 群信息
 *
 * @author Anker
 */
@Data
@TableName("chat_group")
public class ChatGroup {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 群名
     */
    private String groupName;
    /**
     * 群公告
     */
    private String groupAnnouncement;
    /**
     * 群简介
     */
    private String groupIntro;
    /**
     * 群主
     */
    private Long owner;
    /**
     * 创建人
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private LocalDateTime createAt;
    /**
     * 更新时间
     */
    private LocalDateTime updateAt;
}
