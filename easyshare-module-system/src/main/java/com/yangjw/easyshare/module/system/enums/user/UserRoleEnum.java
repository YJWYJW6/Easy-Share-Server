package com.yangjw.easyshare.module.system.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author yangjw
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    STUDENT(1, "SCHOOL_TEACHER"), //  学生
    SCHOOL_ADMIN(2, "SCHOOL_ADMIN"),// 学校负责人
    SUPER_ADMIN(3, "SUPER_ADMIN"); // 超级管理员

    private final Integer code;
    private final String desc;

    /**
     * 根据 code 获取枚举
     */
    public static String toSecurityRole(Integer code) {
        for (UserRoleEnum r : values()) {
            if (r.code.equals(code)) {
                return "ROLE_" + r.desc;
            }
        }
        return null;
    }

    /**
     * 根据 code 判断是否存在
     */
    public static boolean isExists(Integer code) {
        for (UserRoleEnum r : values()) {
            if (r.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
