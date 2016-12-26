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
    private static Map<String, String> cache = new HashMap<>();

    public TokenCache(int seconds) {
        timer = new Timer();
        timer.schedule(new TokenCacheReminder(), 0, seconds * 1000);
    }

    public TokenCache() {
    }

    private class TokenCacheReminder extends TimerTask {
        public void run() {
            clearExpiredElementsFromCache(cache);
        }
    }

    public void addAuthorizedUser(String username, String token) {
        cache.put(username, token);
    }

    public boolean isExistToken(String token) {
        return cache.containsKey(tokenUtils.parseToken(token).getSubject());
    }

    private void clearExpiredElementsFromCache(Map<String, String> cache) {
        logger.debug("Checking cache.");
        Date currentDate = new Date();

        cache.forEach((k, v) -> {
            if (tokenUtils.parseToken(v).getExpiration().compareTo(currentDate) < 0) {
                cache.remove(k);
                logger.debug("User " + k + " has been deleted from cache.");
            }
        });
    }

}
