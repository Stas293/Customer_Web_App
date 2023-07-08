package com.projects.customer_web_app.validators;

import com.projects.customer_web_app.dto.UserUpdateDto;
import com.projects.customer_web_app.model.User;
import com.projects.customer_web_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUpdateValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateDto userUpdateDto = (UserUpdateDto) target;
        Optional<User> userByEmail = userRepository.findByPersonalInfoEmail(userUpdateDto.getPersonalInfoEmail());
        if (userByEmail.isPresent() && !userByEmail.get().getId().equals(userUpdateDto.getId())) {
            errors.rejectValue("personalInfoEmail", "userUpdateDto.personalInfoEmail.exists");
        }
        if (errors.hasErrors()) {
            log.error("Validation errors were found while updating a user");
        }
    }
}
