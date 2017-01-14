package ua.ievleva.movieland.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.ievleva.movieland.dao.UserDao;
import ua.ievleva.movieland.dao.mapper.UserMapper;
import ua.ievleva.movieland.entity.User;
import ua.ievleva.movieland.exception.InvalidCredentialsException;
import ua.ievleva.movieland.security.token.TokenUtils;

import java.util.Map;
import java.util.Properties;

@Repository
public class UserDaoImpl implements UserDao {

    private static UserMapper USER_MAPPER = new UserMapper();

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private Properties sqlQueries;

    @Override
    public User findUserByCredentials(Map<String, String> credentials) {
        String sql = sqlQueries.getProperty("users.byUserName");

        User user = namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("username", credentials.get("username")),
                USER_MAPPER);

        if (!tokenUtils.isCorrectPassword(credentials.get("password"), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password.");
        }

        return user;
    }

}
