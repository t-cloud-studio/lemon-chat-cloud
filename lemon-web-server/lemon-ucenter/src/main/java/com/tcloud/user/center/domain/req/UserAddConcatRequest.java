package com.tcloud.user.center.domain.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserAddConcatRequest {

    /**
     * 被天骄用户id
     */
    @NotNull(message = "请选择要添加的用户")
    private Long targetUserId;


}
