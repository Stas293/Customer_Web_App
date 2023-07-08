package com.projects.customer_web_app.mapper;

import com.projects.customer_web_app.utility.FullNameInterface;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDetailsMapper implements Mapping<com.projects.customer_web_app.model.User, UserDetails> {
    @Override
    public UserDetails map(com.projects.customer_web_app.model.User user) {
        FullNameInterface fullName = user::getFullName;
        Set<Method> methods = Set.of(FullNameInterface.class.getMethods());
        User userDetails = new User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );
        List<Class<?>> collect = Arrays.stream(User.class.getInterfaces())
                .collect(Collectors.toList());
        collect.add(FullNameInterface.class);
        Class<?>[] interfaces = collect.toArray(new Class[0]);

        return (UserDetails) Proxy.newProxyInstance(
                UserDetailsMapper.class.getClassLoader(),
                interfaces,
                (proxy, method, args) -> {
                    if (methods.contains(method)) {
                        return method.invoke(fullName, args);
                    }
                    return method.invoke(userDetails, args);
                });
    }
}
