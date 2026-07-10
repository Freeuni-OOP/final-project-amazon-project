package com.amazon.amazon_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String userDir = System.getProperty("user.dir");
        String absolutePhotosPath = Paths.get(userDir, "src", "backend", "main", "resources", "static", "photos")
                .toAbsolutePath()
                .toUri()
                .toString();

        registry.addResourceHandler("/photos/**")
                .addResourceLocations(absolutePhotosPath);
    }
}
