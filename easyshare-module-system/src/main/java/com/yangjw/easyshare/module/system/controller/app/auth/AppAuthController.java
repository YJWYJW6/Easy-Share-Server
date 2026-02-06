package com.yangjw.easyshare.module.system.controller.app.auth;

import com.yangjw.easyshare.framework.common.pojo.CommonResult;
import com.yangjw.easyshare.module.system.controller.vo.auth.AuthLoginReqVO;
import com.yangjw.easyshare.module.system.controller.vo.auth.AuthLoginRespVO;
import com.yangjw.easyshare.module.system.service.auth.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "app端 - 认证")
@RestController
@RequestMapping("/app/system/auth")
@Validated
@RequiredArgsConstructor
public class AppAuthController {

    private final IAuthService authService;

    /**
     * 登录
     */
    @Operation(summary = "使用账号密码登录")
    @PostMapping("/login")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return CommonResult.success(authService.loginApp(reqVO));
    }


    /**
     * 退出
     */
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public CommonResult<Void> logout() {
        return CommonResult.success();
    }
}
