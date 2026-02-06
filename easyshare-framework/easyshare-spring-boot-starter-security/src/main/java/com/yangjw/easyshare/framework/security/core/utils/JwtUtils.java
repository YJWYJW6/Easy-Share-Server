package com.yangjw.easyshare.framework.security.core.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.yangjw.easyshare.framework.security.config.properties.EasyShareSecurityProperties;
import com.yangjw.easyshare.framework.security.core.pojo.CurrentLoginUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * <p>
 * 主要职责：
 * 1）生成 JWT Token（用于登录）
 * 2）从 HttpServletRequest 提取 Token（支持 header / 参数）
 * 3）校验 Token 是否有效（签名 + 过期时间）
 * 4）解析 Token 中的用户信息（uid / ut / un）
 * <p>
 *
 * @author yangjw
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final EasyShareSecurityProperties properties;

    // 用户 ID
    public static final String CLAIM_USER_ID = "uid";

    // 用户类型
    public static final String CLAIM_USER_ROLE = "uo";

    // 用户名
    public static final String CLAIM_USERNAME = "un";

    // 用户状态
    public static final String CLAIM_USER_STATUS = "us";

    // 用户认证状态
    public static final String CLAIM_VERIFY_STATUS = "uvs";

    // 学校
    public static final String CLAIM_SCHOOL_ID = "usc";

    // Spring Security 权限标识
    public static final String CLAIM_ROLE_KEY = "rk";


    /**
     * 生成 JWT Token
     */
    public String generateToken(CurrentLoginUser loginUser) {
        // 参数校验
        if (loginUser == null || loginUser.getUserId() == null) {
            throw new IllegalArgumentException("loginUser/userId 不能为空");
        }

        // 过期时间
        DateTime now = DateUtil.date();
        DateTime expireAt = DateUtil.offset(now, DateField.SECOND,
                Math.toIntExact(properties.getJwtExpireSeconds()));

        // JWT payload
        Map<String, Object> payload = new HashMap<>();
        payload.put(RegisteredPayload.ISSUER, properties.getJwtIssuer()); // 签发者
        payload.put(RegisteredPayload.ISSUED_AT, now); // 签发时间
        payload.put(RegisteredPayload.EXPIRES_AT, expireAt); // 过期时间

        // 自定义字段（业务字段）
        payload.put(CLAIM_USER_ID, loginUser.getUserId()); // 用户 ID
        payload.put(CLAIM_USER_ROLE, loginUser.getRole()); // 用户角色
        payload.put(CLAIM_USERNAME, loginUser.getUsername()); // 用户名
        payload.put(CLAIM_USER_STATUS, loginUser.getStatus()); // 用户状态
        payload.put(CLAIM_SCHOOL_ID, loginUser.getSchoolId()); //  学校
        payload.put(CLAIM_VERIFY_STATUS, loginUser.getVerifyStatus()); // 用户认证状态
        payload.put(CLAIM_ROLE_KEY, loginUser.getRole()); // Spring Security 权限标识

        // 使用 secret 对 token 签名（HS256）
        return JWTUtil.createToken(payload, getSecretKey());
    }


    /**
     * 校验 token 是否有效
     * <p>
     * 校验内容：
     * 1）签名是否正确（secret 是否一致）
     * 2）是否过期（exp）
     */
    public boolean validate(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            return false;
        }
        try {
            // 1）构造签名器（HS256 + secret）
            JWTSigner signer = JWTSignerUtil.createSigner("HS256", getSecretKey());

            // 2）校验签名是否正确
            JWTValidator.of(token).validateAlgorithm(signer);

            // 3）校验时间（exp / nbf 等）
            JWTValidator.of(token).validateDate(new Date());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断 token 是否过期
     *
     * @param token JWT Token
     * @return true=已过期；false=未过期
     */
    public boolean isExpired(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            return true;
        }
        Date expireAt = (Date) parse(token).getPayload(RegisteredPayload.EXPIRES_AT);
        return expireAt == null || expireAt.before(new Date());
    }

    /**
     * 将 token 解析为 JWT 对象
     *
     * @param token JWT Token
     * @return JWT 对象（可继续读取 payload 的字段）
     */
    public JWT parse(String token) {
        return JWTUtil.parseToken(token);
    }

    /**
     * 解析指定 Claim 字段
     * <p>
     * 如果 Claim 字段不存在，则返回 null
     */
    public Object parseClaim(String token, String key) {
        if (CharSequenceUtil.isBlank(token)) {
            return null;
        }
        return parse(token).getPayload(key);
    }

    /**
     * 解析 token 中的 userId
     *
     * @param token JWT Token
     * @return userId，解析失败返回 null
     */
    public Long parseUserId(String token) {
        Object uid = parseClaim(token, CLAIM_USER_ID);
        return uid == null ? null : Long.valueOf(uid.toString());
    }

    /**
     * 解析 token 中的 role
     *
     * @param token JWT Token
     * @return role，解析失败返回 null
     */
    public String parseRole(String token) {
        Object ut = parseClaim(token, CLAIM_USER_ROLE);
        return ut == null ? null : ut.toString();
    }

    /**
     * 解析 token 中的 username
     *
     * @param token JWT Token
     * @return username，解析失败返回 null
     */
    public String parseUsername(String token) {
        Object un = parseClaim(token, CLAIM_USERNAME);
        return un == null ? null : un.toString();
    }

    /**
     * 从请求头中获取 token，并自动去除前缀（如 "Bearer "）
     *
     * @param request    HttpServletRequest
     * @param headerName 请求头名，例如 "Authorization"
     * @param prefix     前缀，例如 "Bearer "（注意一般带空格）
     * @return token（去除前缀后的纯 token），取不到返回 null
     */
    public String getToken(HttpServletRequest request, String headerName, String prefix) {
        String value = request.getHeader(headerName);
        if (CharSequenceUtil.isBlank(value)) {
            return null;
        }

        // 允许传 null/空，则直接返回
        if (CharSequenceUtil.isBlank(prefix)) {
            return value.trim();
        }

        // 不区分大小写匹配 Bearer 前缀
        if (CharSequenceUtil.startWithIgnoreCase(value, prefix)) {
            return value.substring(prefix.length()).trim();
        }

        // 如果没有前缀，就直接当作 token 返回
        return value.trim();
    }

    /**
     * 从 token 解析出 CurrentLoginUser（会先校验 token）
     */
    public CurrentLoginUser parseLoginUser(String token) {
        if (!validate(token)) {
            return null;
        }

        CurrentLoginUser user = new CurrentLoginUser();
        user.setUserId(parseUserId(token));
        user.setRole(parseRole(token));
        user.setUsername(parseUsername(token));

        // 其他字段
        Object us = parseClaim(token, CLAIM_USER_STATUS);
        if (us != null) {
            user.setStatus(Integer.valueOf(us.toString()));
        }

        Object uvs = parseClaim(token, CLAIM_VERIFY_STATUS);
        if (uvs != null) {
            user.setVerifyStatus(Integer.valueOf(uvs.toString()));
        }

        Object usc = parseClaim(token, CLAIM_SCHOOL_ID);
        if (usc != null) {
            user.setSchoolId(Long.valueOf(usc.toString()));
        }

        Object rk = parseClaim(token, CLAIM_ROLE_KEY);
        if (rk != null) {
            user.setRole(rk.toString());
        }

        return user;
    }

    /**
     * 获取密钥（byte[]）
     */
    private byte[] getSecretKey() {
        return properties.getJwtSecret().getBytes(StandardCharsets.UTF_8);
    }
}