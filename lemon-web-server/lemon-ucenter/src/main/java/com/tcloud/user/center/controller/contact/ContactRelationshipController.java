package com.tcloud.user.center.controller.contact;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcloud.component.token.utils.TokenUtil;
import com.tcloud.user.center.domain.req.UserAddConcatRequest;
import com.tcloud.user.center.domain.req.UserAgreeEventRequest;
import com.tcloud.user.center.domain.req.UserFriendRelationshipRequest;
import com.tcloud.user.center.domain.vo.ContactInfoVO;
import com.tcloud.user.center.domain.vo.UserContactPageVO;
import com.tcloud.user.center.service.contact.ContactRelationshipService;
import com.tcloud.web.common.domain.PageRequest;
import com.tcloud.web.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户联系人关系前端控制器
 *
 * @author Anker
 */
@RestController
@RequestMapping("/web/ucenter/contact")
public class ContactRelationshipController {


    @Autowired
    private ContactRelationshipService contactRelationshipService;


    /**
     * 好友列表
     *
     * @param request 请求参数
     * @return {@link Page<UserContactPageVO>}
     */
    @PostMapping("contact_list")
    public R<Page<UserContactPageVO>> contactList(@RequestBody PageRequest<UserFriendRelationshipRequest> request) {
        return R.data(contactRelationshipService.contactList(request));
    }

    /**
     * 添加联系人
     *
     * @param request
     * @return
     */
    @PostMapping("add_contact")
    public R<Void> addContact(@RequestBody UserAddConcatRequest request) {
        contactRelationshipService.addContact(request, TokenUtil.getRequestUserId());
        return R.success();
    }

    /**
     * to add list
     *
     * @param request param
     * @return {@link Page<UserContactPageVO>}
     */
    @PostMapping("to_add_list")
    public R<Page<UserContactPageVO>> userToAddList(@RequestBody PageRequest<?> request) {
        return R.data(contactRelationshipService.userToAddList(request, TokenUtil.getRequestUserId()));
    }


    /**
     * to add list
     *
     * @param id param
     * @return {@link Page<UserContactPageVO>}
     */
    @GetMapping("to_add_contact_info/{id}")
    public R<UserContactPageVO> getToAddContactInfo(@PathVariable("id") Long id) {
        return R.data(contactRelationshipService.getToAddContactInfo(id, TokenUtil.getRequestUserId()));
    }


    /**
     * agree or disagree
     *
     * @param request request
     * @return Non
     */
    @PostMapping("agree_event")
    public R<Void> agreeEvent(@RequestBody UserAgreeEventRequest request) {
        contactRelationshipService.doAgreeEvent(request, TokenUtil.getRequestUserId());
        return R.success();
    }


    /**
     * 删除联系人
     *
     * @param userId
     * @return
     */
    @PostMapping("delete_contact/{userId}")
    public R<Void> deleteContact(@PathVariable("userId") Long userId) {
        return R.success();
    }

    /**
     * 联系人拉入黑名单
     *
     * @param userId 用户id
     * @return
     */
    @PostMapping("block_contact/{userId}")
    public R<Void> blockContact(@PathVariable("userId") Long userId) {
        return R.success();
    }

    /**
     * 查看好友信息
     *
     * @param userId 用户id
     * @return {@link R<ContactInfoVO>}
     */
    @GetMapping("contact_info/{userId}")
    public R<ContactInfoVO> getContactInfo(@PathVariable("userId") Long userId) {
        return R.success();
    }

}
