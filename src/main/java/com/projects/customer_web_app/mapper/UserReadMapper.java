package com.projects.customer_web_app.mapper;

import com.projects.customer_web_app.dto.UserReadDto;
import com.projects.customer_web_app.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserReadMapper extends Mapping<UserReadDto, User> {
    @Override
    @org.mapstruct.Mapping(source = "addressCityName", target = "address.city.name")
    @org.mapstruct.Mapping(source = "addressStreet", target = "address.street")
    @org.mapstruct.Mapping(source = "personalInfoDateOfCreation", target = "personalInfo.dateOfCreation")
    @org.mapstruct.Mapping(source = "personalInfoEmail", target = "personalInfo.email")
    @org.mapstruct.Mapping(source = "personalInfoLastName", target = "personalInfo.lastName")
    @org.mapstruct.Mapping(source = "personalInfoFirstName", target = "personalInfo.firstName")
    User map(UserReadDto userReadDto);

    @InheritInverseConfiguration(name = "map")
    UserReadDto toDto(User user);
}