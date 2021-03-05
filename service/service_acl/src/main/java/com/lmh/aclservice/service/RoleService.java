package com.lmh.aclservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.aclservice.entity.Role;

import java.util.List;
import java.util.Map;

/**
 *  服务类
 * @author ZengJinming
 * @since 2020-04-16
 */
public interface RoleService extends IService<Role> {

    //根据用户获取角色数据
    Map<String, Object> findRoleByUserId(String userId);

    //根据用户分配角色
    void saveUserRoleRealtionShip(String userId, String[] roleId);

    List<Role> selectRoleByUserId(String id);
}
