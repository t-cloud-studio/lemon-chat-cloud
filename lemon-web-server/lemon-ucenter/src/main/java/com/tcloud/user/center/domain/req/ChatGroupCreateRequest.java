package com.tcloud.user.center.domain.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ChatGroupCreateRequest {



    @NotNull(message = "请指定群成员")
    @Size(min = 3, max = 500, message = "群成员数量错误,至少3人至多500人")
    private List<Integer> groupMembers;


}
