package com.projects.customer_web_app.validators;

import com.projects.customer_web_app.dto.UserRegistrationDto;
import com.projects.customer_web_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserRegistrationValidator implements Validator {
    private final UserRepository userRepository;
    private final UserCaptchaValidator captchaValidator;
    private final UserCaptchaValidator userCaptchaValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationDto userRegistrationDto = (UserRegistrationDto) target;
        CompletableFuture<Boolean> captchaFuture = CompletableFuture.supplyAsync(() -> userCaptchaValidator.checkCaptcha(userRegistrationDto.getCaptchaResponse()));
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", null, "Passwords do not match");
        }
        if (userRepository.existsByPersonalInfoEmail(userRegistrationDto.getEmail())) {
            errors.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (userRepository.existsByUsername(userRegistrationDto.getUsername())) {
            errors.rejectValue("username", null, "There is already an account registered with that username");
        }
        if (Boolean.FALSE.equals(captchaFuture.join())) {
            errors.rejectValue("captchaResponse", null, "Captcha is not valid");
        }
        if (errors.hasErrors()) {
            log.error("Validation errors were found while registering a new user");
        }
    }
}
