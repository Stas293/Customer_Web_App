package com.projects.customer_web_app.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
@Embeddable
public class PersonalInfo {
    private String firstName;
    private String lastName;
    private String email;
    @CreatedDate
    private LocalDate dateOfCreation;
}