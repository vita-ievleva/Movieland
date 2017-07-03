package ua.ievleva.movieland.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ua.ievleva.movieland.security.token.TokenCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;


public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenCache tokenCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Pre handle request");
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (tokenCache.isExistToken(token)) {
                logger.info("Token has been found in cache. User is Logged in");
            } else {
                logger.error("Token has not been found in cache.");
                response.setStatus(401);
                return false;
            }
        return true;
    }

}
