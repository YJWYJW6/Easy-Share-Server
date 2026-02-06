package com.yangjw.easyshare.framework.openapi.config;

import com.yangjw.easyshare.framework.openapi.config.properties.EasyShareOpenApiProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Openapi 配置文件
 */
@Configuration
@EnableConfigurationProperties(EasyShareOpenApiProperties.class)
public class OpenApiConfig {

    /**
     * 文档基础信息（标题/描述/作者等）
     */

    @Bean
    public OpenAPI openAPI(EasyShareOpenApiProperties props) {
        EasyShareOpenApiProperties.Contact c = props.getContact();

        return new OpenAPI().info(new Info()
                .title(props.getTitle())
                .description(props.getDescription())
                .version(props.getVersion())
                .contact(new Contact()
                        .name(c.getName())
                        .email(c.getEmail())
                        .url(c.getUrl())
                ));
    }

    /**
     * 0 基础
     * 例如：/infra/**
     */
    @Bean
    public GroupedOpenApi infraApi() {
        return GroupedOpenApi.builder()
                .group("0-基础(Infra)")
                .pathsToMatch("/infra/**")
                .build();
    }

    /**
     * 1 管理端接口分组
     * 例如：/admin/**
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("1-管理端(Admin)")
                .pathsToMatch("/admin/**")
                .build();
    }

    /**
     * 2 用户端（网页 H5/PC）接口分组
     * 例如：/app/**
     */
    @Bean
    public GroupedOpenApi appApi() {
        return GroupedOpenApi.builder()
                .group("2-用户端(Web)")
                .pathsToMatch("/app/**")
                .build();
    }
}
