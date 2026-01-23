package com.yangjw.easyshare.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录VO - request
 *
 * @author yangjw
 */
@Data
@Schema(description = "管理后台 - 管理员登录 Request VO")
public class AuthLoginReqVO {

    @NotBlank(message = "账号不能为空")
    @Schema(description = "用户名", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
