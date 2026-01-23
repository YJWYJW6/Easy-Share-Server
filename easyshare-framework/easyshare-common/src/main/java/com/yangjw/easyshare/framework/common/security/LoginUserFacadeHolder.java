package com.yangjw.easyshare.framework.common.security;

public class LoginUserFacadeHolder {

    /**
     * 登录用户 Facade
     */
    private static volatile LoginUserFacade FACADE;

    /**
     * 私有构造方法，避免被实例化
     */
    private LoginUserFacadeHolder() {}

    /**
     * 设置登录用户 Facade
     *
     * @param facade 登录用户 Facade
     */
    public static void set(LoginUserFacade facade) {
        FACADE = facade;
    }

    /**
     * 获取登录用户 ID
     *
     * @return 登录用户 ID
     */
    public static Long getLoginUserId() {
        return FACADE == null ? null : FACADE.getLoginUserId();
    }
}