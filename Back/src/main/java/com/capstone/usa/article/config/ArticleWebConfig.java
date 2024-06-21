package com.capstone.usa.article.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ArticleWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imgFile/**")
                .addResourceLocations("file:///C:/Users/User/Desktop/24-1st-capston-USA/Back/src/main/resources/static/imgFile/");
    }
}
