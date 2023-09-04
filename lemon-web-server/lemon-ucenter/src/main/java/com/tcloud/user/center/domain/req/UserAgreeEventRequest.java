package com.tcloud.user.center.domain.req;

import com.tcloud.user.center.enums.ContactAgreeStatus;
import lombok.Data;

@Data
public class UserAgreeEventRequest {


    private Long relationId;


    private ContactAgreeStatus agreeStatus;


}
