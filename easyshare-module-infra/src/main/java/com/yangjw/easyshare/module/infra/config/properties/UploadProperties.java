package com.yangjw.easyshare.module.infra.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "easy-share.infra.upload")
public class UploadProperties {
    /**
     * 文件保存的本地目录
     */
    private String basePath;

    /**
     * 访问前缀（通过静态资源映射暴露）
     */
    private String baseUrl;

    /**
     * 最大上传大小（字节），默认 5MB
     */
    private long maxSize = 5 * 1024 * 1024;

    /**
     * 允许上传的文件类型（后缀）
     */
    private List<String> allowExt = List.of("jpg", "jpeg", "png", "webp");
}
