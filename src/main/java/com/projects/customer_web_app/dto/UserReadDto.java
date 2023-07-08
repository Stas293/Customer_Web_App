package com.projects.customer_web_app.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.projects.customer_web_app.model.User}
 */
@Builder
@Dto
public record UserReadDto(
        Long id,
        String username,
        String imagePath,
        String personalInfoFirstName,
        String personalInfoLastName,
        String personalInfoEmail,
        LocalDate personalInfoDateOfCreation,
        String addressStreet,
        String addressCityName
) implements Serializable {
}