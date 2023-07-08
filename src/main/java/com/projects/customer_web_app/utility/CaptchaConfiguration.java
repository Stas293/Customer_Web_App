package com.projects.customer_web_app.utility;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.recaptcha.key")
public record CaptchaConfiguration (
        String requestMethod,
        String secretKey,
        String requestUrl,
        String siteKey
) {
}
