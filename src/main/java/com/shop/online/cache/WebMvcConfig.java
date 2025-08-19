package com.shop.online.cache;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiCacheInterceptor apiCacheInterceptor;

    public WebMvcConfig(ApiCacheInterceptor apiCacheInterceptor) {
        this.apiCacheInterceptor = apiCacheInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiCacheInterceptor)
                .addPathPatterns("/**"); // cache tất cả API
    }
}
