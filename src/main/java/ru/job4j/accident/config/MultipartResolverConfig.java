package ru.job4j.accident.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Резолвер multipart-запросов.
 * Используется для удобной работы с файлами в запросах.
 */
@Configuration
public class MultipartResolverConfig {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10 * 1024 * 1024);
        return multipartResolver;
    }

}