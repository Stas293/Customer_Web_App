package com.projects.customer_web_app.dto;

import com.projects.customer_web_app.model.City;

import java.io.Serializable;

/**
 * DTO for {@link City}
 */
public record CityReadDto(
        Long id,
        String name
) implements Serializable {
}