package com.yangjw.easyshare.framework.common.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AvatarUtils {

    /**
     * DiceBear API
     * 你可以换不同的风格：
     *  - adventurer
     *  - avataaars
     *  - bottts
     *  - identicon
     *  - micah
     */
    private static final String DEFAULT_STYLE = "adventurer";

    /**
     * 生成随机头像 URL（DiceBear）
     */
    public static String randomDiceBearAvatar() {
        return diceBearAvatar(DEFAULT_STYLE, randomSeed());
    }

    /**
     * 根据用户名/用户id生成稳定头像（同一个 seed 永远同一个头像）
     */
    public static String diceBearAvatarByUsername(String username) {
        String seed = StrUtil.isBlank(username) ? randomSeed() : username.trim();
        return diceBearAvatar(DEFAULT_STYLE, seed);
    }

    /**
     * 生成 DiceBear 头像 URL
     *
     * @param style 风格
     * @param seed  种子（决定头像长相）
     */
    public static String diceBearAvatar(String style, String seed) {
        // DiceBear v9: https://api.dicebear.com/9.x/{style}/svg?seed=xxx
        String s = URLEncoder.encode(seed, StandardCharsets.UTF_8);
        return "https://api.dicebear.com/9.x/" + style + "/svg?seed=" + s;
    }

    private static String randomSeed() {
        // seed 越随机越好：用户注册时调用一次并存库
        return RandomUtil.randomString(10) + "-" + IdUtil.fastSimpleUUID();
    }
}