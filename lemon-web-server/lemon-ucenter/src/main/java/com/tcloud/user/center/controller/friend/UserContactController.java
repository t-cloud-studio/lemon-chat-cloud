package com.tcloud.user.center.controller.friend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloud.user.center.domain.req.UserFriendRelationshipRequest;
import com.tcloud.web.common.domain.PageRequest;
import com.tcloud.web.common.r.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户好友关系
 *
 * @author Anker
 */
@RestController
@RequestMapping("/web/ucenter/contact")
public class UserContactController {

    /**
     * 好友列表
     *
     * @param request   请求参数
     * @return {@link R<Page<Object>>}
     */
    @PostMapping("contact_list")
    public R<Page<Object>> friendList(@RequestBody PageRequest<UserFriendRelationshipRequest> request){
        return R.success();
    }








}
