package com.frakton.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve Angular app under /mfa-ui/
        registry.addResourceHandler("/mfa-ui/**")
                .addResourceLocations("classpath:/static/mfa-ui/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        // If the requested resource exists, return it; otherwise, return the index.html
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                                : new ClassPathResource("/static/mfa-ui/index.html");
                    }
                });

        // Fallback to serve index.html for any other paths (e.g., deep links in Angular routing)
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/mfa-ui/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        return new ClassPathResource("/static/mfa-ui/index.html");
                    }
                });
    }
}
