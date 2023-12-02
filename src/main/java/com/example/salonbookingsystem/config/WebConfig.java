package com.example.salonbookingsystem.config;

import com.example.salonbookingsystem.interceptors.ServicesInterceptor;
import com.example.salonbookingsystem.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ServiceService serviceService;

    private final CacheManager cacheManager;

    @Autowired
    public WebConfig(ServiceService serviceService,
                     CacheManager cacheManager) {
        this.serviceService = serviceService;
        this.cacheManager = cacheManager;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ServicesInterceptor(this.cacheManager,
                        this.serviceService))
                .addPathPatterns("/make-reservation/**");
    }
}
