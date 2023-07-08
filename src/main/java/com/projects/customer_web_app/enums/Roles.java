package com.projects.customer_web_app.enums;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String code;

    Roles(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
