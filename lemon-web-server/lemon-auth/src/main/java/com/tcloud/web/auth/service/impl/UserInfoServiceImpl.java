package com.tcloud.web.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcloud.web.auth.domain.entity.UserInfo;
import com.tcloud.web.auth.mapper.UserInfoMapper;
import com.tcloud.web.auth.service.UserInfoService;
import com.tcloud.web.common.utils.EncryptUtil;
import org.springframework.stereotype.Service;

import static com.tcloud.web.common.constants.CoreConstant.DEFAULT_NICKNAME_PREFIX;

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


    @Override
    public UserInfo createUser(String phone, String cipher) {
        // 盐
        String salt = RandomUtil.randomString(6);
        // 密码(密文)
        String encryptCipher = EncryptUtil.md5Encrypt(cipher, salt);
        // 构建用户实体
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo.setCipher(encryptCipher);
        userInfo.setNickname(this.generateDefaultNickName());
        userInfo.setCipherSalt(salt);
        // 落库
        this.save(userInfo);
        return userInfo;
    }


    private String generateDefaultNickName() {
        String s = RandomUtil.randomString(6);
        return StrUtil.builder(DEFAULT_NICKNAME_PREFIX).append(s).toString();
    }


    @Override
    public UserInfo selectUserInfoByPhone(String phone) {
        return baseMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getPhone, phone));
    }
}
