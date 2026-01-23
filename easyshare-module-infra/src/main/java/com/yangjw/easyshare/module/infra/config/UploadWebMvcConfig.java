package com.yangjw.easyshare.module.infra.config;

import com.yangjw.easyshare.module.infra.config.properties.UploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class UploadWebMvcConfig implements WebMvcConfigurer {

    private final UploadProperties uploadProperties;

    /**
     * 配置静态资源映射
     * 说明：
     * 把请求路径 /uploads/** 中匹配到的 /** 部分，拼接到磁盘目录 basePath 后面
     *
     * @param registry 资源映射注册
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String basePath = uploadProperties.getBasePath();
        if (!basePath.endsWith("/")) {
            basePath = basePath + "/";
        }
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + basePath);
    }
}