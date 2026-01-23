package com.yangjw.easyshare.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 用户登录VO - response
 *
 * @author yangjw
 */
@Data
@Builder
@Schema(description = "管理后台 - 管理员登录 Request VO")
public class AuthLoginRespVO {

    /**
     * JWT token
     */
    @Schema(description = "token", example = "skdjfkjsd")
    private String token;

    /**
     * 过期时间（秒）
     */
    @Schema(description = "过期时间（秒）", example = "3600")
    private Long expiresIn;

}
