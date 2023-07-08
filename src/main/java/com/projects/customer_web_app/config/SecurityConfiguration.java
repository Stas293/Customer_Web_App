package com.projects.customer_web_app.config;

import com.projects.customer_web_app.enums.PublicAccessPaths;
import com.projects.customer_web_app.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .requestMatchers(request ->
                                request.getServletPath().contains("admin")
                        ).hasRole(Roles.ADMIN.name())
                        .requestMatchers(request ->
                                request.getServletPath().contains("user")
                        ).hasRole(Roles.USER.name())
                        .requestMatchers(
                                configureAccessEnum(PublicAccessPaths.values())
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                )
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService)
                        )
                )
                .build();
    }

    private <T extends Enum<T>> String[] configureAccessEnum(Enum<T>[] values) {
        return Arrays.stream(values)
                .map(Enum::toString)
                .toArray(String[]::new);
    }
}
