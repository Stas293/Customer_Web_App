package com.projects.customer_web_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.projects.customer_web_app.repository")
public class SQLDatabaseConfiguration {
}
