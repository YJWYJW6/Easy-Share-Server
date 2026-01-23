package com.yangjw.easyshare.framework.security.core.filter;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangjw.easyshare.framework.common.exception.ServiceException;
import com.yangjw.easyshare.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.yangjw.easyshare.framework.common.pojo.CommonResult;
import com.yangjw.easyshare.framework.security.config.properties.EasyShareSecurityProperties;
import com.yangjw.easyshare.framework.security.core.pojo.CurrentLoginUser;
import com.yangjw.easyshare.framework.security.core.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


/**
 * Token 认证过滤器（核心）
 *
 * @author yangjw
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final EasyShareSecurityProperties properties;

    private final JwtUtils jwtUtils;

    /**
     * 拦截请求，进行 token 校验
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            log.info("开始进行 Token 认证，url:{}", request.getRequestURI());

            // 放行 OPTIONS 请求
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                chain.doFilter(request, response);
                return;
            }

            // 1. 获取白名单
            String uri = request.getRequestURI();
            if (matchIgnoreUrl(uri)) {
                log.info("白名单url：{} 放行", uri);
                chain.doFilter(request, response);
                return;
            }

            // 2. 从请求头拿 Token
            String token = jwtUtils.getToken(request, properties.getTokenHeader(), properties.getTokenPrefix());
            // token 缺失
            if (StrUtil.isBlank(token)) {
                log.info("token 缺失：{} 拦截", token);
                throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
            }

            // 3. 校验 Token 是否 合法
            CurrentLoginUser currentLoginUser = jwtUtils.parseLoginUser(token);
            if (ObjectUtil.isEmpty(currentLoginUser)) {
                log.info("token 非法：{} 拦截", token);
                throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
            }

            // 4. 保存用户信息到 SecurityContextHolder
            if (ObjectUtil.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
                saveToSecurityContext(request, currentLoginUser);
            }
            // 放行
            log.info("token 验证通过：{} 放行", token);
            chain.doFilter(request, response);

        } catch (ServiceException ex) {
            writeJson(response, CommonResult.error(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            writeJson(response, CommonResult.error(
                    GlobalErrorCodeConstants.UNKNOWN.getCode(),
                    GlobalErrorCodeConstants.UNKNOWN.getMsg()
            ));
            log.error("[系统异常]", ex);
        }
    }

    /**
     * 保存用户信息到 SecurityContextHolder
     *
     * @param request   请求
     * @param loginUser 用户信息
     */
    private void saveToSecurityContext(HttpServletRequest request, CurrentLoginUser loginUser) {
        // 1. 获取角色键，封装权限
        String roleKey = loginUser.getRoleKey();
        List<SimpleGrantedAuthority> authorities =
                StrUtil.isBlank(roleKey)
                        ? List.of()
                        : List.of(new SimpleGrantedAuthority(roleKey));

        // 2. 创建 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        loginUser, // principal 放 loginUser
                        authorities // 权限集合
                );

        // 3. 把 request 相关信息绑定进去（IP、sessionId 等）
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 4. 存入 SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void writeJson(HttpServletResponse response, CommonResult<?> result) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }

    /**
     * 匹配忽略的 URL
     */
    private boolean matchIgnoreUrl(String uri) {
        List<String> patterns = properties.getJwtIgnoreUrls();
        if (patterns == null || patterns.isEmpty()) {
            return false;
        }
        for (String pattern : patterns) {
            if (PATH_MATCHER.match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }
}
