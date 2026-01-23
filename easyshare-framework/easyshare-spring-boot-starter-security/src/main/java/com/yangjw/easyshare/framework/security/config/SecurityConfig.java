package com.yangjw.easyshare.framework.security.config;

import com.yangjw.easyshare.framework.security.config.properties.EasyShareSecurityProperties;
import com.yangjw.easyshare.framework.security.core.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * EasyShare Spring Security 配置类
 * <p>
 * 特点：
 * 1) 不使用 Session（前后端分离 token 模式）
 * 2) 支持白名单 permit-all
 * 3) 支持 security.enabled 一键开关
 * 4) 添加 TokenAuthenticationFilter 做 token 认证
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableConfigurationProperties(EasyShareSecurityProperties.class)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final EasyShareSecurityProperties properties;

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * 创建 SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 允许跨域
                .cors(Customizer.withDefaults())
                // 禁用 CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 关闭 Session（无状态，完全依赖 token）
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 关闭表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 配置请求授权规则
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                // 把 token 过滤器放到 UsernamePasswordAuthenticationFilter 之前
                .addFilterBefore(tokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Spring Security 加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(properties.getPasswordEncoderLength());
    }
}
