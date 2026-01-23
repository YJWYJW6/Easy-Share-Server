package com.yangjw.easyshare.module.system.service.auth;

import com.yangjw.easyshare.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import com.yangjw.easyshare.module.system.controller.admin.auth.vo.AuthLoginRespVO;

/**
 * 用户 Service 接口
 *
 * @author yangjw
 */
public interface IAuthService {

    /**
     * App端登录（学生/教师）
     */
    AuthLoginRespVO loginApp(AuthLoginReqVO reqVO);

    /**
     * 管理端登录（管理员）
     */
    AuthLoginRespVO loginAdmin(AuthLoginReqVO reqVO);
}
