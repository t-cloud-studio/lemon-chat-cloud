package com.tcloud.user.center.controller.user;

import com.tcloud.common.obj.vo.UserInfoVO;
import com.tcloud.web.common.r.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/ucenter")
public class UserInfoController {


    @GetMapping("info/{userId}")
    public R<UserInfoVO> getUserInfo(@PathVariable("userId") Long userId){
        return R.success();
    }


}
