package com.yangjw.easyshare.module.system.config.propreties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 系统配置
 */
@Data
@ConfigurationProperties(prefix = "easy-share.system")
public class EasyShareSysProperties {

    private String defaultPassword = "123456";

}