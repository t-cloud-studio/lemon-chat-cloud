package com.tcloud.web.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloud.web.auth.domain.entity.UserInfo;
import com.tcloud.web.auth.mapper.UserInfoMapper;
import com.tcloud.web.auth.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public UserInfo findValidUserById(Integer userId) {
        return null;
    }

    @Override
    public UserInfo existsPhone(String phone) {
        return baseMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getPhone, phone));
    }
}
