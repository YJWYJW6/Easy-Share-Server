package com.yangjw.easyshare.framework.openapi.config;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

@AutoConfiguration(before = Knife4jAutoConfiguration.class)
@ConditionalOnClass(OpenAPI.class)
@Import(Knife4jOpenApiCustomizer.class)
public class EasyShareSwaggerAutoConfiguration {
}