package com.yangjw.easyshare.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.yangjw.easyshare.server",
        "com.yangjw.easyshare.module"}
)
@MapperScan(basePackages = "com.yangjw.easyshare.module.**.dal.mysql")
public class EasyShareServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyShareServerApplication.class, args);
    }
}
