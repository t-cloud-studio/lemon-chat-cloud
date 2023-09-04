package com.tcloud.user.center.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloud.user.center.domain.entity.user.UserInfo;
import com.tcloud.user.center.mapper.user.UserInfoMapper;
import com.tcloud.user.center.service.user.UserInfoService;
import com.tcloud.web.common.enums.BoolEnum;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


    @Override
    public UserInfo findValidUserById(Integer userId) {
        return baseMapper.selectOne(Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getId, userId)
                .eq(UserInfo::getDeleted, BoolEnum.FALSE.getVar()));
    }
}
