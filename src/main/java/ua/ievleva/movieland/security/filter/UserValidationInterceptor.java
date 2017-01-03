package ua.ievleva.movieland.security.filter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua.ievleva.movieland.security.token.TokenUtils;

import static ua.ievleva.movieland.entity.Role.ROLE_USER;


@Component
public class UserValidationInterceptor implements MethodInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
        String token = requestAttributes.getRequest().getHeader("Authorization");

        if (token == null || !tokenUtils.checkUserRole(ROLE_USER, token)) {

            logger.error("User does not have permissions for this operation.");
            return null;
        }

        logger.debug("User is authorized.");

        return invocation.proceed();
    }
}
