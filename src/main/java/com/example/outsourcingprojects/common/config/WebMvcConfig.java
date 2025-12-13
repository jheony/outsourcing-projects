package com.example.outsourcingprojects.common.config;

import com.example.outsourcingprojects.common.filter.HttpServletWrappingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<HttpServletWrappingFilter> firstFilterRegister() {
        FilterRegistrationBean<HttpServletWrappingFilter> registrationBean =
                new FilterRegistrationBean<>(new HttpServletWrappingFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);

        return registrationBean;
    }
}
