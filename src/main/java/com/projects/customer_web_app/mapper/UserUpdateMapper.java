package com.projects.customer_web_app.mapper;

import com.projects.customer_web_app.model.User;
import com.projects.customer_web_app.dto.UserUpdateDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserUpdateMapper extends com.projects.customer_web_app.mapper.Mapping<UserUpdateDto, User> {
    @Override
    @org.mapstruct.Mapping(source = "addressCityName", target = "address.city.name")
    @org.mapstruct.Mapping(source = "city", target = "address.city.id")
    @org.mapstruct.Mapping(source = "addressStreet", target = "address.street")
    @org.mapstruct.Mapping(source = "personalInfoEmail", target = "personalInfo.email")
    @org.mapstruct.Mapping(source = "personalInfoLastName", target = "personalInfo.lastName")
    @org.mapstruct.Mapping(source = "personalInfoFirstName", target = "personalInfo.firstName")
    User map(UserUpdateDto userUpdateDto);

    @InheritInverseConfiguration(name = "map")
    UserUpdateDto toDto(User user);

    @InheritConfiguration(name = "map")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Override
    User update(UserUpdateDto userUpdateDto, @MappingTarget User user);
}