package ua.ievleva.movieland.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.ievleva.movieland.security.token.TokenCache;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenCache tokenCache;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        try {

            if (tokenCache.isExistToken(token)) {
                logger.info("Token has been found in cache. User is Logged in");
                chain.doFilter(request, response);
            }

        } catch (Exception e) {
            logger.debug("Token is invalid or not found in cache.", e);

            httpServletResponse.setStatus(401);
        }
    }

    @Override
    public void destroy() {

    }
}
