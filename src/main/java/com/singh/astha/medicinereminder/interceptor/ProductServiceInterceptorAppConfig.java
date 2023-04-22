package com.singh.astha.medicinereminder.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ProductServiceInterceptorAppConfig implements WebMvcConfigurer {

    private final LoggingInterceptor productServiceInterceptor;

    public ProductServiceInterceptorAppConfig(
            LoggingInterceptor productServiceInterceptor) {
        this.productServiceInterceptor = productServiceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(productServiceInterceptor);
    }

}
