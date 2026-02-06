package com.yangjw.easyshare.framework.security.core.pojo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CurrentLoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户主键ID
     */
    private Long userId;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 用户类型
     */
    private String role;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 学校
     */
    private Long schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 认证状态
     */
    private Integer verifyStatus;

}
