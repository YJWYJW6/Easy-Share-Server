package com.yangjw.easyshare.framework.security.core.facade;

import com.yangjw.easyshare.framework.common.security.LoginUserFacade;
import com.yangjw.easyshare.framework.common.security.LoginUserFacadeHolder;
import com.yangjw.easyshare.framework.security.core.pojo.CurrentLoginUser;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 基于 Spring Security 的登录用户 Facade 实现
 *
 * 作用：
 * - 从 SecurityContextHolder 中获取 CurrentLoginUser
 * - 提供给 common / datapermission 等模块使用
 */
@Component
public class SecurityLoginUserFacade implements LoginUserFacade {

    @PostConstruct
    public void init() {
        // 注册到 common 层的 Holder
        LoginUserFacadeHolder.set(this);
    }

    @Override
    public Long getLoginUserId() {
        CurrentLoginUser user = getCurrentLoginUser();
        return user == null ? null : user.getUserId();
    }

    @Override
    public String getLoginUserRole() {
        CurrentLoginUser user = getCurrentLoginUser();
        return user == null ? null : user.getRole();
    }

    @Override
    public Long getLoginUserSchoolId() {
        CurrentLoginUser user = getCurrentLoginUser();
        return user == null ? null : user.getSchoolId();
    }

    /**
     * 从 Spring Security 中获取当前登录用户
     *
     * @return CurrentLoginUser or null
     */
    private CurrentLoginUser getCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CurrentLoginUser)) {
            return null;
        }

        return (CurrentLoginUser) principal;
    }
}