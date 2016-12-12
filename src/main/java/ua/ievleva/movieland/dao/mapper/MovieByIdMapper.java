package ua.ievleva.movieland.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.ievleva.movieland.entity.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MovieByIdMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet rs, int i) throws SQLException {
        Movie movie = new Movie();

        movie.setId(rs.getLong("id"));
        movie.setTitle(rs.getString("name"));
        movie.setYearOfRelease(rs.getString("year"));
        movie.setCountry(rs.getString("country"));
        movie.setDescription(rs.getString("description"));

        return movie;
    }
}
