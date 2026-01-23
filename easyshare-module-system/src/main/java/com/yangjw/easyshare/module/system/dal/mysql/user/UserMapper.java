package com.yangjw.easyshare.module.system.dal.mysql.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangjw.easyshare.module.system.dal.dataobject.user.UserDO;

import java.time.LocalDateTime;

/**
 * 用户 Mapper
 *
 * @author yangjw
 */
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 根据用户名查询用户(唯一)
     */
    default UserDO selectByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, username);
        return selectOne(queryWrapper);
    }

    /**
     * 根据手机号查询用户(唯一)
     */
    default UserDO selectByPhone(String phone) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getPhone, phone);
        return selectOne(queryWrapper);
    }

    /**
     * 根据手机号查询用户(唯一)
     */
    default UserDO selectByEmail(String email) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getEmail, email);
        return selectOne(queryWrapper);
    }

    /**
     * 根据 ID 更新登录时间和登录IP
     */
    default Integer updateLoginInfoById(Long id, LocalDateTime loginTime, String loginIp) {
        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setLastLoginTime(loginTime);
        userDO.setLastLoginIp(loginIp);
        return updateById(userDO);
    }

    /**
     * 根据 ID 查询角色
     */
    default Integer selectRoleById(Long id) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getId, id);
        return selectOne(queryWrapper).getRole();
    }
}
