package com.projects.customer_web_app.service;

import com.projects.customer_web_app.mapper.Mapping;
import com.projects.customer_web_app.model.City;
import com.projects.customer_web_app.dto.CityReadDto;
import com.projects.customer_web_app.repository.CitiesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitiesService {
    private final CitiesRepository citiesRepository;
    private final Mapping<City, CityReadDto> cityReadMapper;

    public List<CityReadDto> getCities(String search) {
        return cityReadMapper.map(citiesRepository.findAllByNameContainingIgnoreCase(search));
    }
}
