package com.tcloud.user.center.service.contact;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloud.user.center.domain.entity.contact.UserContactRelationship;
import com.tcloud.user.center.domain.req.UserAddConcatRequest;
import com.tcloud.user.center.domain.req.UserAgreeEventRequest;
import com.tcloud.user.center.domain.req.UserFriendRelationshipRequest;
import com.tcloud.user.center.domain.vo.UserContactPageVO;
import com.tcloud.web.common.domain.PageRequest;

public interface ContactRelationshipService extends IService<UserContactRelationship> {
    /**
     * 联系人列表
     *
     * @param request
     * @return
     */
    Page<UserContactPageVO> contactList(PageRequest<UserFriendRelationshipRequest> request);

    /**
     * 添加联系人
     *
     * @param request
     * @param requestUserId
     */
    void addContact(UserAddConcatRequest request, Long requestUserId);

    Page<UserContactPageVO> userToAddList(PageRequest<?> request, Long requestUserId);

    UserContactPageVO getToAddContactInfo(Long id, Long requestUserId);

    void doAgreeEvent(UserAgreeEventRequest request, Long requestUserId);

    boolean hasRelation(Long createUserId, Integer userId);
}
