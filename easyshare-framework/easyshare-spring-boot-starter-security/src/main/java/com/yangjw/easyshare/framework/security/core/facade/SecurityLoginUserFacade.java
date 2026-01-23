package com.yangjw.easyshare.framework.security.core.facade;

import com.yangjw.easyshare.framework.common.security.LoginUserFacade;
import com.yangjw.easyshare.framework.common.security.LoginUserFacadeHolder;
import com.yangjw.easyshare.framework.security.core.pojo.CurrentLoginUser;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityLoginUserFacade implements LoginUserFacade {

    @PostConstruct
    public void init() {
        LoginUserFacadeHolder.set(this);
    }

    @Override
    public Long getLoginUserId() {
        // 获取当前登录用户
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        // 获取用户信息
        Object principal = auth.getPrincipal();
        if (!(principal instanceof CurrentLoginUser)) {
            return null;
        }
        // 获取用户编号
        return ((CurrentLoginUser) principal).getUserId();
    }
}