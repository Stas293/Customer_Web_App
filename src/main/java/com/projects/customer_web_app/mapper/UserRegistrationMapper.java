package com.projects.customer_web_app.mapper;

import com.projects.customer_web_app.dto.UserRegistrationDto;
import com.projects.customer_web_app.model.Address;
import com.projects.customer_web_app.model.PersonalInfo;
import com.projects.customer_web_app.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper extends Mapping<UserRegistrationDto, User> {
    @org.mapstruct.Mapping(target = "address", expression = "java(map(userRegistrationDto.getAddress()))")
    @org.mapstruct.Mapping(target = "personalInfo", expression = "java(mapPersonal(userRegistrationDto))")
    User map(UserRegistrationDto userRegistrationDto);

    default Address map(String address) {
        return Address.builder()
                .street(address)
                .build();
    }

    default PersonalInfo mapPersonal(UserRegistrationDto userRegistrationDto) {
        return PersonalInfo.builder()
                .firstName(userRegistrationDto.getFirstName())
                .lastName(userRegistrationDto.getLastName())
                .email(userRegistrationDto.getEmail())
                .build();
    }
}
