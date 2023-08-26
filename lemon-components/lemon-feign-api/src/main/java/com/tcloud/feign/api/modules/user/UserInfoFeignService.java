package com.tcloud.feign.api.modules.user;

import com.tcloud.feign.api.domain.vo.UserInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO 继续完善, 不要喷我,我随便先写这儿
 *
 * @author evans
 * @description
 * @date 2023/8/26
 */
@FeignClient()
public interface UserInfoFeignService {

    /**
     * 获取用户登录信息
     *
     * @param userId 用户id
     * @return {@link  UserInfoVO} 登录用户信息
     */
    @GetMapping("feign/user_service/getLoginUserInfo")
    UserInfoVO getLoginUserInfo(Long userId);


}
