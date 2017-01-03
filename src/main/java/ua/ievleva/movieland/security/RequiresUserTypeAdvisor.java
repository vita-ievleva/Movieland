package ua.ievleva.movieland.security;


import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.ievleva.movieland.security.annotation.RequiresUserType;
import ua.ievleva.movieland.security.filter.UserValidationInterceptor;

import java.lang.reflect.Method;

@Component
public class RequiresUserTypeAdvisor extends AbstractPointcutAdvisor {

    @Autowired
    private UserValidationInterceptor userValidationInterceptor;

    private final StaticMethodMatcherPointcut pointcut = new
            StaticMethodMatcherPointcut() {
                @Override
                public boolean matches(Method method, Class<?> targetClass) {
                    return method.isAnnotationPresent(RequiresUserType.class);
                }
            };

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.userValidationInterceptor;
    }
}