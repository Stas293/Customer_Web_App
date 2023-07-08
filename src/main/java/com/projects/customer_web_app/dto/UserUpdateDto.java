package com.projects.customer_web_app.dto;

import com.projects.customer_web_app.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link User}
 */
@Dto
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
        private Long id;
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]{2,255}$", message = "First name must contain only letters")
        private String personalInfoFirstName;
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]{2,255}$", message = "First name must contain only letters")
        private String personalInfoLastName;
        @NotBlank
        @Email
        @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Email must be valid")
        private String personalInfoEmail;
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9\\s.,'-]{2,255}$", message = "Address must contain only letters, numbers and spaces")
        private String addressStreet;
        private Long city;
        private String addressCityName;
}