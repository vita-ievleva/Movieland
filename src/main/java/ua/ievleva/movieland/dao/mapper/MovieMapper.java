package ua.ievleva.movieland.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.ievleva.movieland.entity.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MovieMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setTitle(resultSet.getString("name"));
        return movie;
    }
}
