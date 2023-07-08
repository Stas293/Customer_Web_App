package com.projects.customer_web_app.utility;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

@UtilityClass
public class Utility {
    public <F, S, R> R getObjectImplementingMultipleInterfaces(F first, S second, Class<R> result) {
        Set<Method> userMethods = Set.of(UserDetails.class.getMethods());
        return result.cast(Proxy.newProxyInstance(
                Utility.class.getClassLoader(),
                new Class[]{UserDetails.class, second.getClass().getInterfaces()[0]},
                (proxy, method, args) -> {
                    if (userMethods.contains(method)) {
                        return method.invoke(first, args);
                    }
                    return method.invoke(second, args);
                }));
    }
}
