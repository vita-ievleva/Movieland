package ua.ievleva.movieland.dao.mapper;


import org.springframework.jdbc.core.RowMapper;
import ua.ievleva.movieland.entity.Role;
import ua.ievleva.movieland.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        List<Role> authority = new ArrayList<>();
        authority.add(Role.valueOf(rs.getString("authority")));

        user.setPassword(rs.getString("password"));
        user.setUserName(rs.getString("username"));
        user.setRole(authority);
        user.setEnabled(rs.getString("enabled"));

        return user;
    }
}
