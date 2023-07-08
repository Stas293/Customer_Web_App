package com.projects.customer_web_app.utility;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.image")
public record ImageConfiguration(
        String basePath
) {
}
