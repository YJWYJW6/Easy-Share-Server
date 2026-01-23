package com.yangjw.easyshare.module.system.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author yangjw
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    ENABLE(1, "ENABLE"),
    DISABLE(0, "DISABLE");

    private final Integer code;
    private final String desc;
}
