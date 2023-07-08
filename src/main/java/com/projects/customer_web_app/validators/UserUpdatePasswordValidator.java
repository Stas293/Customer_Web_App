package com.projects.customer_web_app.validators;

import com.projects.customer_web_app.dto.UserUpdatePasswordDto;
import com.projects.customer_web_app.model.User;
import com.projects.customer_web_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUpdatePasswordValidator implements Validator {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdatePasswordDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdatePasswordDto userUpdatePasswordDto = (UserUpdatePasswordDto) target;
        if (!userUpdatePasswordDto.newPassword().equals(userUpdatePasswordDto.newPasswordConfirm())) {
            errors.rejectValue("confirmNewPassword", null, "Passwords do not match");
        }
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        if (!passwordEncoder.matches(userUpdatePasswordDto.oldPassword(), user.getPassword())) {
            errors.rejectValue("oldPassword", null, "Old password is not correct");
        }
        if (errors.hasErrors()) {
            log.error("Validation errors were found while updating a user's password");
        }
    }
}
