package com.phycholee.blog.authorization.config;

import com.phycholee.blog.authorization.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by PhychoLee on 2017/4/16 21:19.
 * Description: 拦截器配置
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/admin/**");
        super.addInterceptors(registry);
    }
}
