package com.yangjw.easyshare.framework.openapi.config;

import com.github.xiaoymin.knife4j.core.conf.GlobalConstants;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jSetting;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

/**
 * 覆盖 Knife4j 默认 OpenApiCustomizer，解决 springdoc 2.8.x 兼容问题
 */
@Primary
@Configuration
@Slf4j
public class Knife4jOpenApiCustomizer
        extends com.github.xiaoymin.knife4j.spring.extension.Knife4jOpenApiCustomizer
        implements GlobalOpenApiCustomizer {

    private final Knife4jProperties knife4jProperties;

    public Knife4jOpenApiCustomizer(Knife4jProperties knife4jProperties,
                                    SpringDocConfigProperties properties) {
        super(knife4jProperties, properties);
        this.knife4jProperties = knife4jProperties;
    }

    @Override
    public void customise(OpenAPI openApi) {
        if (!knife4jProperties.isEnable()) {
            return;
        }

        Knife4jSetting setting = knife4jProperties.getSetting();
        OpenApiExtensionResolver resolver =
                new OpenApiExtensionResolver(setting, knife4jProperties.getDocuments());
        resolver.start();

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(GlobalConstants.EXTENSION_OPEN_SETTING_NAME, setting);
        objectMap.put(GlobalConstants.EXTENSION_OPEN_MARKDOWN_NAME, resolver.getMarkdownFiles());

        openApi.addExtension(GlobalConstants.EXTENSION_OPEN_API_NAME, objectMap);
    }
}