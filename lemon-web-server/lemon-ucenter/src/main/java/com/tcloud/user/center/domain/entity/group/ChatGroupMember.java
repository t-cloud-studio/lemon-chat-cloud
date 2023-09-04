package com.tcloud.user.center.domain.entity.group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tcloud.user.center.domain.entity.user.UserInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("chat_group_member")
public class ChatGroupMember {

    public ChatGroupMember(Long userId, String nickname, String avatar) {
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private LocalDateTime createAt;
    /**
     * 更新时间
     */
    private LocalDateTime updateAt;


}
