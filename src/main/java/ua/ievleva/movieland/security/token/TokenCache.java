package ua.ievleva.movieland.security.token;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class TokenCache {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenUtils tokenUtils;

    private Timer timer;
    private Map<String, String> cache = new HashMap<>();

    public TokenCache(int seconds) {
        timer = new Timer();
        timer.schedule(new TokenCacheReminder(), 0, seconds * 1000);
    }

    public TokenCache() {
    }

    private class TokenCacheReminder extends TimerTask {
        public void run() {
            logger.debug("Checking cache.");
            Date currentDate = new Date();

            cache.forEach((k, v) -> {
                if (tokenUtils.parseToken(v).getExpiration().before(currentDate)) {
                    cache.remove(k);
                    logger.debug("User " + k + " has been deleted from cache.");
                }
            });
        }
    }

    public void addAuthorizedUser(String username, String token) {
        cache.put(username, token);
    }

    public boolean isExistToken(String token) {
        try {
            return cache.containsKey(tokenUtils.parseToken(token).getSubject());
        } catch(Exception e) {
            logger.error("Failed to parse token", e);
            return false;
        }
    }

}
