package com.projects.customer_web_app.bpp;

import com.projects.customer_web_app.dto.Dto;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InjectValidatorListFiltrationBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private static final String MAIN_PACKAGE = "com.projects.customer_web_app";
    private static final String DTO_PACKAGE = MAIN_PACKAGE + ".dto";
    private static final String VALIDATION_PACKAGE = MAIN_PACKAGE + ".validators";
    public static final String VALIDATOR_MAP = "java.util.Map<java.lang.Class<?>, java.util.List<org.springframework.validation.Validator>>";
    private final Set<Class<?>> dtoClasses;
    @Setter
    private ApplicationContext applicationContext;

    public InjectValidatorListFiltrationBeanPostProcessor() {
        Reflections reflections = new Reflections(DTO_PACKAGE);
        this.dtoClasses = reflections.getTypesAnnotatedWith(Dto.class);
    }

    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, String beanName) throws BeansException {
        if (!beanName.contains("Service")) {
            return bean;
        }
        String[] camelCaseBeanName = beanName.split("(?=\\p{Upper})");
        String capitalizedBeanName = StringUtils.capitalize(camelCaseBeanName[0]);
        List<Class<?>> validatorClassesForService = dtoClasses.stream()
                .filter(dtoClass -> dtoClass.getSimpleName().startsWith(capitalizedBeanName))
                .toList();
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field -> field.getType().isAssignableFrom(Map.class))
                .filter(field -> field.getGenericType().getTypeName().contains(VALIDATOR_MAP))
                .forEach(field -> setListValidators(bean, validatorClassesForService, field));
        return bean;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private void setListValidators(Object bean, List<Class<?>> validatorClassesForService, Field field) {
        field.setAccessible(true);
        Map<Class<?>, List<Validator>> validators = (applicationContext.getBeansOfType(Validator.class)).values()
                .stream()
                .filter(validator -> validator.getClass()
                        .getPackage()
                        .getName()
                        .contains(VALIDATION_PACKAGE))
                .filter(validator -> validatorClassesForService.stream()
                        .anyMatch(validator::supports))
                .collect(Collectors.groupingBy(v -> validatorClassesForService.stream()
                        .filter(v::supports)
                        .findFirst()
                        .orElseThrow()));
        field.set(bean, validators);
        field.setAccessible(false);
    }
}
