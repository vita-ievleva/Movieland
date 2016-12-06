package ua.ievleva.movieland.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.ievleva.movieland.dao.MovieDao;
import ua.ievleva.movieland.dao.mapper.MovieMapper;
import ua.ievleva.movieland.entity.Movie;

import java.util.List;

@Service
public class MovieDaoImpl implements MovieDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAllMovies() {
        String sqlGetMovies = "select movie.id, name, year, rate, genre from movie inner join movie_genres on  movie.id=movie_genres.id_movie;";

        return jdbcTemplate.query(sqlGetMovies, new MovieMapper());

    }
}
