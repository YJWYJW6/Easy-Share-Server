package com.yangjw.easyshare.framework.security.core.utils;

import cn.hutool.core.util.ObjectUtil;
import com.yangjw.easyshare.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.yangjw.easyshare.framework.security.core.pojo.CurrentLoginUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Security 工具类
 *
 * @author yangjw
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    /**
     * 获取 Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 是否已登录
     */
    public static boolean isLogin() {
        Authentication authentication = getAuthentication();
        if (ObjectUtil.isEmpty(authentication.getPrincipal())) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        return principal instanceof CurrentLoginUser;
    }

    /**
     * 获取当前登录用户（可能返回 null）
     */
    public static CurrentLoginUser getLoginUser() {
        // 未登录，返回 null
        if (!isLogin()) {
            return null;
        }
        // 获取当前登录用户
        Object principal = getAuthentication().getPrincipal();
        return (CurrentLoginUser) principal;
    }

    /**
     * 获取当前登录用户的 IP
     */
    public static String getLoginUserIP() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object details = authentication.getDetails();
        if (details instanceof WebAuthenticationDetails webDetails) {
            return webDetails.getRemoteAddress();
        }
        return null;
    }

    /**
     * 获取当前登录用户（必须登录，否则抛异常）
     */
    public static CurrentLoginUser getLoginUserRequired() {
        CurrentLoginUser loginUser = getLoginUser();
        if (ObjectUtil.isEmpty(loginUser)) {
            throw new IllegalStateException(GlobalErrorCodeConstants.UNAUTHORIZED.getMsg());
        }
        return loginUser;
    }

    public static Long getLoginUserId() {
        CurrentLoginUser user = getLoginUserRequired();
        return user.getUserId();
    }

    public static String getLoginUsername() {
        CurrentLoginUser user = getLoginUserRequired();
        return user.getUsername();
    }

    public static Integer getLoginUserRole() {
        CurrentLoginUser user = getLoginUserRequired();
        return user.getRole();
    }

    public static Integer getLoginUserStatus() {
        CurrentLoginUser user = getLoginUserRequired();
        return user.getStatus();
    }

    public static Integer getLoginVerifyStatus() {
        CurrentLoginUser user = getLoginUserRequired();
        return user.getVerifyStatus();
    }

    public static Long getLoginSchoolId() {
        CurrentLoginUser user = getLoginUserRequired();
        return user.getSchoolId();
    }

    public static void clearContext() {
        SecurityContextHolder.clearContext();
    }
}
