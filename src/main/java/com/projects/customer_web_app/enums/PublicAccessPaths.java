package com.projects.customer_web_app.enums;

public enum PublicAccessPaths {
    HOME("/"),
    CSS("/css/**"),
    JS("/js/**"),
    LOGIN("/login"),
    REGISTRATION("/register"),
    CONTACT_US("/contact_us"),
    RESET("/reset"),
    RESET_PASSWORD("/reset-password"),
    NEWS_API("/api/v1/cities/**"),
    ERROR("/error"),
    API_DOCS("/v3/api-docs/**"),
    SWAGGER_UI("/swagger-ui/**");

    private final String path;

    PublicAccessPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
