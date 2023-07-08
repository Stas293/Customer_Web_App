package com.projects.customer_web_app.http.rest;

import com.projects.customer_web_app.dto.CityReadDto;
import com.projects.customer_web_app.service.CitiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@Slf4j
@RequiredArgsConstructor
public class CitiesRestController {
    private final CitiesService citiesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityReadDto> getCities(String search) {
        return citiesService.getCities(search);
    }
}
