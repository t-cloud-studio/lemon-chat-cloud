package com.tcloud.user.center.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloud.user.center.domain.entity.user.UserInfo;
import com.tcloud.user.center.mapper.user.UserInfoMapper;
import com.tcloud.user.center.service.user.IUserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
}
