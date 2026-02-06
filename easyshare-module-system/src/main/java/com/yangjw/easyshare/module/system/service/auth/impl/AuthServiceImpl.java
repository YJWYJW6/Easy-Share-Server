package com.yangjw.easyshare.module.system.service.auth.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yangjw.easyshare.framework.common.exception.ServiceException;
import com.yangjw.easyshare.framework.security.config.properties.EasyShareSecurityProperties;
import com.yangjw.easyshare.framework.security.core.pojo.CurrentLoginUser;
import com.yangjw.easyshare.framework.security.core.utils.JwtUtils;
import com.yangjw.easyshare.framework.security.core.utils.SecurityUtils;
import com.yangjw.easyshare.module.system.controller.vo.auth.AuthLoginReqVO;
import com.yangjw.easyshare.module.system.controller.vo.auth.AuthLoginRespVO;
import com.yangjw.easyshare.module.system.convert.auth.CurrentLoginUserConvert;
import com.yangjw.easyshare.module.system.dal.dataobject.user.UserDO;
import com.yangjw.easyshare.module.system.enums.SysErrorCodeConstants;
import com.yangjw.easyshare.module.system.enums.auth.LoginType;
import com.yangjw.easyshare.module.system.enums.user.UserStatusEnum;
import com.yangjw.easyshare.framework.common.enums.UserRoleEnum;
import com.yangjw.easyshare.module.system.service.auth.IAuthService;
import com.yangjw.easyshare.module.system.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户 Service 接口
 *
 * @author yangjw
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    // service
    private final IUserService userService;

    // convert
    private final CurrentLoginUserConvert currentLoginUserConvert;

    // properties
    private final EasyShareSecurityProperties properties;

    // util
    private final JwtUtils jwtUtils;

    /**
     * APP 登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    @Override
    public AuthLoginRespVO loginApp(AuthLoginReqVO reqVO) {
        return doLogin(reqVO, LoginType.APP);
    }

    /**
     * 管理端登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    @Override
    public AuthLoginRespVO loginAdmin(AuthLoginReqVO reqVO) {
        return doLogin(reqVO, LoginType.ADMIN);
    }

    private AuthLoginRespVO doLogin(AuthLoginReqVO reqVO, LoginType loginType) {
        // 0. 当前时间
        LocalDateTime now = LocalDateTime.now();

        // 1. 查询用户
        UserDO user = userService.getUserByUsername(reqVO.getUsername());

        // 2. 通用校验
        checkUserValid(user);
        checkPassword(reqVO.getPassword(), user.getPassword());

        // 3. 登录类型校验
        switch (loginType) {
            case APP -> checkStudent(user);
            case ADMIN -> checkAdmin(user);
            default -> throw new IllegalStateException("未知登录类型");
        }

        // 4. 记录登录信息
        userService.recordLoginLog(
                user.getId(),
                now,
                SecurityUtils.getLoginUserIP()
        );

        // 5. 构建 JWT
        CurrentLoginUser currentLoginUser = currentLoginUserConvert.convert(user);
        String token = jwtUtils.generateToken(currentLoginUser);

        // 6. 返回结果
        return AuthLoginRespVO.builder()
                .token(token)
                .expiresIn(properties.getJwtExpireSeconds())
                .build();
    }

    /**
     * 校验是否是管理员（学校管理员 or 超级管理员）
     */
    private void checkAdmin(UserDO user) {
        if (!UserRoleEnum.SUPER_ADMIN.getRoleKey().equals(user.getRole()) &&
                !UserRoleEnum.SCHOOL_ADMIN.getRoleKey().equals(user.getRole())) {
            throw new ServiceException(SysErrorCodeConstants.AUTH_LOGIN_NON_ADMIN_RESTRICTION);
        }
    }

    /**
     * 校验学生
     */
    private void checkStudent(UserDO user) {
        if (!UserRoleEnum.STUDENT.getRoleKey().equals(user.getRole())) {
            throw new ServiceException(SysErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        }
    }

    /**
     * 校验用户是否合法
     */
    private void checkUserValid(UserDO user) {
        // 1. 校验账号是否存在
        if (user == null) {
            // 可能密码错误，也可能用户不存在
            throw new ServiceException(SysErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 2. 校验状态
        if (ObjectUtil.equal(user.getStatus(), UserStatusEnum.DISABLE.getCode())) {
            throw new ServiceException(SysErrorCodeConstants.USER_STATUS_DISABLED);
        }
    }

    /**
     * 校验密码是否匹配
     */
    private void checkPassword(String rawPassword, String encodedPassword) {
        if (Boolean.FALSE.equals(userService.isPasswordMatch(rawPassword, encodedPassword))) {
            throw new ServiceException(SysErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        }
    }
}
