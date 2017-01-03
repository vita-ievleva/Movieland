package ua.ievleva.movieland.controller;

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
import ua.ievleva.movieland.dao.UserDao;
import ua.ievleva.movieland.entity.User;
import ua.ievleva.movieland.security.token.TokenUtils;
import ua.ievleva.movieland.security.token.TokenCache;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

@PropertySource("classpath:security.properties")
@RestController("/auth/login")
public class AuthenticationController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenUtils tokenUtils;

    @Value("${jwt.expiration.hours}")
    private String expiration;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenCache tokenCache;

    @PostMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity authenticateUser(@RequestBody Map<String, String> credentials) {
        User user;

        try {
            user = userDao.findUserByCredentials(credentials);
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
