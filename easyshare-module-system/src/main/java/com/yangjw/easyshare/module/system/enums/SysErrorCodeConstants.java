package com.yangjw.easyshare.module.system.enums;

import com.yangjw.easyshare.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 * <p>
 * system 系统，使用 1-002-000-000 段
 */
public interface SysErrorCodeConstants {

    // ========== AUTH 模块 1-002-001-000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_002_001_000, "用户名或密码错误");
    ErrorCode AUTH_LOGIN_NON_ADMIN_RESTRICTION   = new ErrorCode(1_002_001_001, "非管理员账号，禁止登录管理端");

    // ========== 用户模块 1-002-002-000 ==========
    ErrorCode USER_STATUS_DISABLED = new ErrorCode(1_002_002_000, "用户被禁用");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_002_002_001, "用户不存在");
    ErrorCode USER_USERNAME_DUPLICATE = new ErrorCode(1_002_002_002, "用户名已存在");
    ErrorCode USER_PHONE_DUPLICATE = new ErrorCode(1_002_002_003, "手机号已存在");
    ErrorCode USER_EMAIL_DUPLICATE = new ErrorCode(1_002_002_004, "邮箱已存在");
    ErrorCode USER_CREATE_ROLE_INVALID = new ErrorCode(1_002_002_005, "创建用户角色不合法");
    ErrorCode USER_NO_PERMISSION_CREATE = new ErrorCode(1_002_002_006, "无权限创建此角色");
    ErrorCode USER_NO_PERMISSION_ACCESS = new ErrorCode(1_002_002_007, "无此权限访问此用户");
    ErrorCode USER_ID_NOT_EMPTY = new ErrorCode(1_002_002_008, "用户ID不能为空");


}
