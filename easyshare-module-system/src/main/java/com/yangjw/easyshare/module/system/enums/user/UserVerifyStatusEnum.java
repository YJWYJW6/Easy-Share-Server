package com.yangjw.easyshare.module.system.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户认证状态枚举
 *
 * @author yangjw
 */
@Getter
@AllArgsConstructor
public enum UserVerifyStatusEnum {
    UNVERIFIED(0, "UNVERIFIED"),
    VERIFYING(1, "VERIFYING"),
    VERIFIED(2, "VERIFIED"),
    REJECTED(3, "REJECTED");

    private final Integer code;
    private final String desc;
}
