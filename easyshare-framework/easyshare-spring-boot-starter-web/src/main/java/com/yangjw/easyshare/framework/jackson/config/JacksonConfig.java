package com.yangjw.easyshare.framework.jackson.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));

            // LocalDateTime 格式化
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
            builder.serializers(new LocalDateTimeSerializer(dtf));
            builder.deserializers(new LocalDateTimeDeserializer(dtf));

            // LocalDate 格式化
            DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_PATTERN);
            builder.serializers(new LocalDateSerializer(df));
            builder.deserializers(new LocalDateDeserializer(df));
        };
    }
}
