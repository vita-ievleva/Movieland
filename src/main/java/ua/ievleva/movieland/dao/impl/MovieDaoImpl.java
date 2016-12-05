package ua.ievleva.movieland.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.ievleva.movieland.dao.mapper.MovieMapper;
import ua.ievleva.movieland.entity.Movie;

import java.util.List;

@Service
public class MovieDaoImpl {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Movie> getAllMovies() {
        String sql = "select * from movie;";

        return jdbcTemplate.query(sql, new MovieMapper());

    }
}
