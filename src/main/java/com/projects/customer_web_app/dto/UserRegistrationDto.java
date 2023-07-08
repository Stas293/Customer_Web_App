package com.projects.customer_web_app.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Dto
public class UserRegistrationDto {
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]{3,255}$", message = "Username must be between 3 and 20 characters long and can contain only letters and numbers")
        private String username;
        @NotBlank
        @Size(min = 8, max = 255)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter and one number")
        private String password;
        private String confirmPassword;
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]{2,255}$", message = "First name must contain only letters")
        private String firstName;
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]{2,255}$", message = "Last name must contain only letters")
        private String lastName;
        @NotBlank
        @Email
        @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Email must be valid")
        private String email;
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9\\s.,'-]{2,255}$", message = "Address must contain only letters, numbers and spaces")
        private String address;
        @NotNull
        private Long city;
        private MultipartFile image;
        private String captchaResponse;
}
