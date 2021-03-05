package com.lmh.aclservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.aclservice.entity.UserRole;
import com.lmh.aclservice.mapper.UserRoleMapper;
import com.lmh.aclservice.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author ZengJinming
 * @since 2020-04-16
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
