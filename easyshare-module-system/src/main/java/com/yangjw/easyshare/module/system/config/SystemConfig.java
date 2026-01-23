package com.yangjw.easyshare.module.system.config;

import com.yangjw.easyshare.module.system.config.propreties.EasyShareSysProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(EasyShareSysProperties.class)
@Configuration
public class SystemConfig {}