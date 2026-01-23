package com.yangjw.easyshare.module.infra.config;

import com.yangjw.easyshare.module.infra.config.properties.UploadProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(UploadProperties.class)
@Configuration
public class InfraConfig {
}
