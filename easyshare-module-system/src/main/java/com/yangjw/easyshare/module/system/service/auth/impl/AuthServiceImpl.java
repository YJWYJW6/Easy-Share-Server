package com.yangjw.easyshare.module.system.service.auth.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yangjw.easyshare.framework.common.exception.ServiceException;
import com.yangjw.easyshare.framework.security.config.properties.EasyShareSecurityProperties;
import com.yangjw.easyshare.framework.security.core.pojo.CurrentLoginUser;
import com.yangjw.easyshare.framework.security.core.utils.JwtUtils;
import com.yangjw.easyshare.framework.security.core.utils.SecurityUtils;
import com.yangjw.easyshare.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import com.yangjw.easyshare.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.yangjw.easyshare.module.system.convert.auth.CurrentLoginUserConvert;
import com.yangjw.easyshare.module.system.dal.dataobject.user.UserDO;
import com.yangjw.easyshare.module.system.enums.SysErrorCodeConstants;
import com.yangjw.easyshare.module.system.enums.user.UserStatusEnum;
import com.yangjw.easyshare.module.system.enums.user.UserRoleEnum;
import com.yangjw.easyshare.module.system.service.auth.IAuthService;
import com.yangjw.easyshare.module.system.service.user.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户 Service 接口
 *
 * @author yangjw
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Resource
    private IUserService userService;

    @Resource
    private CurrentLoginUserConvert currentLoginUserConvert;

    @Resource
    private EasyShareSecurityProperties properties;

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public AuthLoginRespVO loginApp(AuthLoginReqVO reqVO) {
        return null;
    }

    @Override
    public AuthLoginRespVO loginAdmin(AuthLoginReqVO reqVO) {
        LocalDateTime now = LocalDateTime.now();

        UserDO user = userService.getUserByUsername(reqVO.getUsername());

        // 校验用户是否合法
        checkUserValid(user);
        // 只允许管理员登录
        checkAdmin(user);
        // 校验密码
        checkPassword(reqVO.getPassword(), user.getPassword());

        // 记录登录信息
        userService.recordLoginLog(user.getId(), now, SecurityUtils.getLoginUserIP());

        // 构建 jwt
        CurrentLoginUser currentLoginUser = currentLoginUserConvert.convert(user);
        // 角色 key，用于 security 获取角色
        currentLoginUser.setRoleKey(UserRoleEnum.toSecurityRole(user.getRole()));
        String token = jwtUtils.generateToken(currentLoginUser);

        // 封装返回对象
        return AuthLoginRespVO.builder()
                .token(token)
                .expiresIn(properties.getJwtExpireSeconds())
                .build();
    }

    /**
     * 校验是否是管理员
     */
    private void checkAdmin(UserDO user) {
        if (!UserRoleEnum.SUPER_ADMIN.getCode().equals(user.getRole()) &&
        !UserRoleEnum.SCHOOL_ADMIN.getCode().equals(user.getRole())) {
            throw new ServiceException(SysErrorCodeConstants.AUTH_LOGIN_NON_ADMIN_RESTRICTION);
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
        if (!userService.isPasswordMatch(rawPassword, encodedPassword)) {
            throw new ServiceException(SysErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        }
    }
}
