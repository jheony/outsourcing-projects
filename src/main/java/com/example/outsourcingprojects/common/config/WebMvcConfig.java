package com.example.outsourcingprojects.common.config;

import com.example.outsourcingprojects.common.aop.ActivityLoggingAccept;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public ActivityLoggingAccept activityLoggingAccept(){
        return new ActivityLoggingAccept();
    }
}
