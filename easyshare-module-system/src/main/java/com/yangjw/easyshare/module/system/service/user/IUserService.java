package com.yangjw.easyshare.module.system.service.user;

import com.yangjw.easyshare.framework.common.pojo.PageResult;
import com.yangjw.easyshare.module.system.controller.admin.user.vo.*;
import com.yangjw.easyshare.module.system.dal.dataobject.user.UserDO;

import java.time.LocalDateTime;

public interface IUserService {

    /**
     * 根据用户名查询用户
     */
    UserDO getUserByUsername(String username);

    /**
     * 校验密码是否匹配
     */
    Boolean isPasswordMatch(String rawPassword, String encodedPassword);

    /**
     * 记录 登录时间 和 登录IP
     */
    Integer recordLoginLog(Long userId, LocalDateTime loginTime, String ip);

    /**
     * 获取当前登录用户信息
     */
    UserInfoRespVO getLoginUserInfo();

    /**
     * 获取用户分页列表
     */
    PageResult<UserPageRespVO> getUserPage(UserPageReqVO reqVO);

    /**
     * 创建用户
     */
    Integer createUser(UserCreateReqVO reqVO);

    /**
     * 修改用户
     */
    Integer updateUserProfile(UserUpdateProfileReqVO reqVO);

    /**
     * 获取用户详情
     */
    UserDetailRespVO getUserDetail(Long id);
}
