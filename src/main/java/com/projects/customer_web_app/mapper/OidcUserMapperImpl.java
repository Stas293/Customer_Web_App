package com.projects.customer_web_app.mapper;

import com.projects.customer_web_app.model.PersonalInfo;
import com.projects.customer_web_app.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;

@Component
public class OidcUserMapperImpl implements Mapping<Map<String, Object>, User> {
    @Override
    public User map(Map<String, Object> from) {
        String email = (String) from.get("email");
        String username = StringUtils.capitalize(email.split("@")[0]);
        String password = UUID.randomUUID().toString();
        String givenName = (String) from.get("given_name");
        String familyName = (String) from.get("family_name");
        return User.builder()
                .username(username)
                .password(password)
                .personalInfo(PersonalInfo.builder()
                        .firstName(givenName)
                        .lastName(familyName)
                        .email(email)
                        .build())
                .build();
    }
}
