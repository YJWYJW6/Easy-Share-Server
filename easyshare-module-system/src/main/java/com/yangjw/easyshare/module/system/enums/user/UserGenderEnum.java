package com.yangjw.easyshare.module.system.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户性别枚举
 *
 * @author yangjw
 */
@Getter
@AllArgsConstructor
public enum UserGenderEnum {
    UNKNOWN(0, "UNKNOWN"),
    MALE(1, "MALE"),
    FEMALE(2, "FEMALE");

    private final Integer code;
    private final String desc;

}
