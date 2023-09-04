package com.tcloud.web.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloud.web.auth.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
