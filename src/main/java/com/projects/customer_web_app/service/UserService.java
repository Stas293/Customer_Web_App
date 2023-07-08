package com.projects.customer_web_app.service;

import com.projects.customer_web_app.dto.UserReadDto;
import com.projects.customer_web_app.dto.UserRegistrationDto;
import com.projects.customer_web_app.dto.UserUpdateDto;
import com.projects.customer_web_app.dto.UserUpdatePasswordDto;
import com.projects.customer_web_app.enums.Roles;
import com.projects.customer_web_app.mapper.Mapping;
import com.projects.customer_web_app.mapper.UserReadMapper;
import com.projects.customer_web_app.mapper.UserUpdateMapper;
import com.projects.customer_web_app.model.Address;
import com.projects.customer_web_app.model.City;
import com.projects.customer_web_app.model.Role;
import com.projects.customer_web_app.model.User;
import com.projects.customer_web_app.repository.CitiesRepository;
import com.projects.customer_web_app.repository.RoleRepository;
import com.projects.customer_web_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CitiesRepository citiesRepository;
    private final RoleRepository roleRepository;
    private final ImageService imageService;
    private final Mapping<UserRegistrationDto, User> userRegistrationDtoToUserMapping;
    private final PasswordEncoder passwordEncoder;
    private final Mapping<User, UserDetails> userDetailsMapping;
    private final UserReadMapper userReadDtoToUserMapping;
    private final UserUpdateMapper userUpdateDtoToUserMapping;
    private Map<Class<?>, List<Validator>> validators;

    @Transactional
    public void save(UserRegistrationDto userRegistrationDto, BindingResult result) {
        List<Validator> validatorList = validators.get(userRegistrationDto.getClass());
        validatorList.forEach(validator -> validator.validate(userRegistrationDto, result));
        if (result.hasErrors()) {
            log.error("Validation errors were found while registering a new user");
            return;
        }
        City city = citiesRepository.findById(userRegistrationDto.getCity()).orElseThrow();
        User user = userRegistrationDtoToUserMapping.map(userRegistrationDto);
        Address address = user.getAddress();
        address.setCity(city);
        user.setAddress(address);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByCode(Roles.USER.code()).orElseThrow();
        user.setRoles(List.of(role));
        uploadImage(userRegistrationDto.getImage(), user.getUsername());
        Optional.ofNullable(userRegistrationDto.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImagePath(user.getUsername() + "/" + image.getOriginalFilename()));
        userRepository.save(user);
    }

    private void uploadImage(MultipartFile image, String username) {
        Optional.ofNullable(image)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> saveImage(file, username));
    }

    @SneakyThrows
    private void saveImage(MultipartFile file, String username) {
        imageService.upload(username, file.getOriginalFilename(), file.getInputStream());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userDetailsMapping::map)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByPersonalInfoEmail(email);
    }

    public UserReadDto getCurrentUser() {
        return userReadDtoToUserMapping.toDto(
                userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                        .orElseThrow());
    }

    public Optional<byte[]> getUserImage() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(User::getImagePath)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

    @Transactional
    public void updateImage(MultipartFile file) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        uploadImage(file, username);
        userRepository.findByUsername(username)
                .ifPresent(user -> user.setImagePath(username + "/" + file.getOriginalFilename()));
    }

    @Transactional
    public void deleteImage() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    imageService.deleteImage(user.getImagePath());
                    user.setImagePath(null);
                });
    }

    public UserUpdateDto getUpdateUserDto() {
        return userUpdateDtoToUserMapping.toDto(
                userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                        .orElseThrow());
    }

    @Transactional
    public void update(UserUpdateDto userUpdateDto, BindingResult bindingResult) {
        List<Validator> validatorList = validators.get(userUpdateDto.getClass());
        validatorList.forEach(validator -> validator.validate(userUpdateDto, bindingResult));
        if (bindingResult.hasErrors()) {
            userUpdateDto.setAddressCityName(citiesRepository.findById(userUpdateDto.getCity()).orElseThrow().getName());
            log.error("Validation errors were found while updating a user");
            return;
        }
        City city = citiesRepository.findById(userUpdateDto.getCity()).orElseThrow();
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        user.getAddress().setCity(city);
        User updated = userUpdateDtoToUserMapping.update(userUpdateDto, user);
        updated.getAddress().setCity(city);
        userRepository.save(updated);
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordDto userUpdatePasswordDto, BindingResult bindingResult) {
        List<Validator> validatorList = validators.get(userUpdatePasswordDto.getClass());
        validatorList.forEach(validator -> validator.validate(userUpdatePasswordDto, bindingResult));
        if (bindingResult.hasErrors()) {
            log.error("Validation errors were found while updating a user password");
            return;
        }
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow();
        user.setPassword(passwordEncoder.encode(userUpdatePasswordDto.newPassword()));
        userRepository.save(user);
    }
}
