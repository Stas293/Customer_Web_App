package com.projects.customer_web_app.mapper;

import com.projects.customer_web_app.model.City;
import com.projects.customer_web_app.dto.CityReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityReadMapper extends Mapping<City, CityReadDto>{
}
