package ua.ievleva.movieland.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.ievleva.movieland.entity.User;
import ua.ievleva.movieland.security.token.TokenCache;
import ua.ievleva.movieland.security.token.TokenUtils;
import ua.ievleva.movieland.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

@PropertySource("classpath:security.properties")
@RestController("/auth/login")
public class AuthenticationController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jwt.expiration.hours}")
    private String expiration;

    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final TokenCache tokenCache;

    @Autowired
    public AuthenticationController(TokenUtils tokenUtils, UserService userService, TokenCache tokenCache) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
        this.tokenCache = tokenCache;
    }

    @PostMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity authenticateUser(@RequestBody Map<String, String> credentials) {
        User user;

        try {
            user = userService.findUserByCredentials(credentials);
        } catch (Exception e) {
            logger.error("Invalid password or username was provided.", e);

            return new ResponseEntity<>("Invalid password or username was provided.",
                    HttpStatus.UNAUTHORIZED);
        }

        String token = tokenUtils.createToken(user, parseInt(expiration));

        tokenCache.addAuthorizedUser(user.getUserName(), token);
        logger.debug("Added user and token to cache.");

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
