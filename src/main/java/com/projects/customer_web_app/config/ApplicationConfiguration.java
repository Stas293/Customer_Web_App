package com.projects.customer_web_app.config;

import com.projects.customer_web_app.utility.ImageConfiguration;
import com.projects.customer_web_app.utility.CaptchaConfiguration;
import org.apache.coyote.ProtocolHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@EnableCaching
@EnableConfigurationProperties({CaptchaConfiguration.class, ImageConfiguration.class})
public class ApplicationConfiguration {
    @Bean
    TomcatProtocolHandlerCustomizer<ProtocolHandler> protocolHandlerCustomizer() {
        return protocolHandler -> protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
