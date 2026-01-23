package com.yangjw.easyshare.framework.security.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyShare 安全配置属性类
 *
 * @author yangjw
 */
@Data
@ConfigurationProperties(prefix = "easy-share.security")
public class EasyShareSecurityProperties {

    /**
     * token 的 header
     */
    private String tokenHeader = "Authorization";

    /**
     * token 的参数名
     */
    private String tokenParameter = "token";

    /**
     * token 前缀
     */
    private String tokenPrefix = "Bearer ";

    /**
     * PasswordEncoder 加密复杂度，越高开销越大
     */
    private Integer passwordEncoderLength = 4;

    /**
     * JWT 密钥
     */
    private String jwtSecret;

    /**
     * JWT 过期时间（秒）
     */
    private Long jwtExpireSeconds;

    /**
     * JWT 签发者
     */
    private String jwtIssuer;

    /**
     * 免登录 URL
     */
    private List<String> jwtIgnoreUrls = new ArrayList<>();

    /**
     * 允许的跨域
     */
    private List<String> allowOrigins = new ArrayList<>();

    /**
     * 允许的跨域方法
     */
    private List<String> allowMethods = new ArrayList<>();

    /**
     * 允许携带 cookie
     */
    private Boolean allowCredentials = true;

    /**
     * 允许的跨域请求头
     */
    private List<String> allowedHeaders = new ArrayList<>();

}