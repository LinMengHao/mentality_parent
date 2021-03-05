package com.lmh.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.aclservice.entity.User;
import com.lmh.aclservice.mapper.UserMapper;
import com.lmh.aclservice.service.UserService;

import org.springframework.stereotype.Service;

/**
 * 用户表 服务实现类
 * @author ZengJinming
 * @since 2020-04-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}
