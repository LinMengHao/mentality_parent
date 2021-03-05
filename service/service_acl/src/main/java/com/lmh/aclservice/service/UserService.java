package com.lmh.aclservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.aclservice.entity.User;


/**
 * 用户表 服务类
 * @author ZengJinming
 * @since 2020-04-16
 */
public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
