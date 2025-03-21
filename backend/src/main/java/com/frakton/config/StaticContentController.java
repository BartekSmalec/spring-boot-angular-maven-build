package com.frakton.config;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StaticContentController {

    private final ResourceLoader resourceLoader;

    public StaticContentController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping(value = {"/mfa-ui", "/mfa-ui/"}, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public Resource serveStaticContent() {
        System.out.println("Serving static content for /mfa-ui");
        Resource resource = resourceLoader.getResource("classpath:/static/mfa-ui/index.html");
        if (!resource.exists()) {
            System.out.println("Resource not found: classpath:/static/mfa-ui/index.html");
        }
        return resource;
    }
}