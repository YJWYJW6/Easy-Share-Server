package com.yangjw.easyshare.module.infra.enums;

import com.yangjw.easyshare.framework.common.exception.ErrorCode;

/**
 * System 基础枚举类
 * <p>
 * infra 系统，使用 1-001-000-000 段
 *
 * @author yangjw
 */
public interface InfraErrorCodeConstants {

    // ========== 文件 模块 1-001-001-000 ==========
    ErrorCode FILE_NOT_EMPTY = new ErrorCode(1_001_001_000, "文件不能为空");
    ErrorCode FILE_TOO_LARGE = new ErrorCode(1_001_001_001, "文件过大");
    ErrorCode FILE_NOT_SUPPORT = new ErrorCode(1_001_001_002, "文件格式不支持");
    ErrorCode FILE_SAVE_ERROR = new ErrorCode(1_001_001_003, "文件保存失败");
}
