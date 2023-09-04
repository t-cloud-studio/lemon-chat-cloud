package com.tcloud.user.center.controller.group;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloud.component.token.utils.TokenUtil;
import com.tcloud.user.center.domain.entity.group.ChatGroup;
import com.tcloud.user.center.domain.req.ChatGroupCreateRequest;
import com.tcloud.user.center.domain.req.ChatGroupPageQueryRequest;
import com.tcloud.user.center.domain.vo.ChatGroupInfoVO;
import com.tcloud.user.center.domain.vo.ChatGroupPageVO;
import com.tcloud.user.center.service.group.ChatGroupService;
import com.tcloud.web.common.domain.PageRequest;
import com.tcloud.web.common.r.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/web/ucenter/chat_group")
public class ChatGroupInfoController {

    private final ChatGroupService chatGroupService;

    /**
     * 群列表
     *
     * @param request
     * @return
     */
    @PostMapping("list")
    public R<Page<ChatGroupPageVO>> list(@RequestBody PageRequest<ChatGroupPageQueryRequest> request){
        return R.success();
    }

    /**
     * 创建群聊
     *
     * @param request
     * @return
     */
    @PostMapping("create")
    public R<ChatGroup> createGroup(@RequestBody @Validated ChatGroupCreateRequest request){
        return R.data(chatGroupService.create(request, TokenUtil.getRequestUserId()));
    }

    /**
     * 群信息
     *
     * @param groupId
     * @return
     */
    @GetMapping("info/{groupId}")
    public R<ChatGroupInfoVO> groupInfo(@PathVariable("groupId") Long groupId){
        return R.success();
    }

    /**
     * 解散群聊
     *
     * @param groupId
     * @return
     */
    @PostMapping("dissolve/{groupId}")
    public R<Void> dissolveGroup(@PathVariable("groupId") Long groupId){
        return R.success();
    }

}
