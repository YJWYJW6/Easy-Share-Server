package com.yangjw.easyshare.framework.common.enums;

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

    STUDENT("ROLE_STUDENT", "student"),
    SCHOOL_ADMIN("ROLE_SCHOOL_ADMIN", "school_admin"),
    SUPER_ADMIN("ROLE_SUPER_ADMIN", "super_admin");

    /**
     * Security / DB 中使用的角色值
     */
    private final String roleKey;

    /**
     * 描述
     */
    private final String desc;

    /* ================= 工具方法 ================= */

    public static boolean isValid(String role) {
        if (role == null) return false;
        for (UserRoleEnum r : values()) {
            if (r.roleKey.equals(role)) {
                return true;
            }
        }
        return false;
    }
}