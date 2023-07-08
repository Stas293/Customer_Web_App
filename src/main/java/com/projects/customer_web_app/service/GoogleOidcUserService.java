package com.projects.customer_web_app.service;

import com.projects.customer_web_app.mapper.Mapping;
import com.projects.customer_web_app.model.User;
import com.projects.customer_web_app.utility.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GoogleOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserService userService;
    private final Mapping<User, UserDetails> userDetailsMapper;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcIdToken idToken = userRequest.getIdToken();
        String email = idToken.getClaim("email");
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "User not found", ""));
        }
        UserDetails userDetails = userDetailsMapper.map(user.get());
        DefaultOidcUser defaultOidcUser = new DefaultOidcUser(
                userDetails.getAuthorities(),
                userRequest.getIdToken());
        return Utility.getObjectImplementingMultipleInterfaces(
                userDetails,
                defaultOidcUser,
                OidcUser.class
        );
    }
}
