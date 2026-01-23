package com.yangjw.easyshare.framework.openapi.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "easyshare.openapi")
public class EasyShareOpenApiProperties {

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 文档版本号
     */
    private String version;

    /**
     * 联系方式
     */
    private Contact contact = new Contact();

    @Data
    public static class Contact {
        private String name;
        private String email;
        private String url;
    }
}
