package ua.ievleva.movieland.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ua.ievleva.movieland.entity.User;

@PropertySource("classpath:security.properties")
@Component
public class TokenUtils {

    @Value("${jwt.secret.key}")
    private String secret;

    public boolean isCorrectPassword(String passwordProvided, String passwordDB) {
        return BCrypt.checkpw(passwordProvided, passwordDB);

    }

    public String createToken(User user, int expiration) {
        Claims claims = Jwts.claims().setSubject(user.getUserName());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(DateTime.now().plusHours(expiration).toDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody();
    }

}
