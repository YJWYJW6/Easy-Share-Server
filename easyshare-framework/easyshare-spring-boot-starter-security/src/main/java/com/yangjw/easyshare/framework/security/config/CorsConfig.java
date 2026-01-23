package com.yangjw.easyshare.framework.security.config;

import com.yangjw.easyshare.framework.security.config.properties.EasyShareSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final EasyShareSecurityProperties properties;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 允许你的前端域名
        config.setAllowedOrigins(properties.getAllowOrigins());

        // 允许携带 cookie
        config.setAllowCredentials(properties.getAllowCredentials());

        // 允许的请求方法
        config.setAllowedMethods(properties.getAllowMethods());

        // 允许前端携带的请求头
        config.setAllowedHeaders(properties.getAllowedHeaders());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
