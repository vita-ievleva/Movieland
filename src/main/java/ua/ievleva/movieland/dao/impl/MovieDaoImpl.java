package ua.ievleva.movieland.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ua.ievleva.movieland.dao.MovieDao;
import ua.ievleva.movieland.dao.mapper.GenresByIdMapper;
import ua.ievleva.movieland.dao.mapper.MovieByIdMapper;
import ua.ievleva.movieland.dao.mapper.MovieMapper;
import ua.ievleva.movieland.dao.mapper.ReviewByIdMapper;
import ua.ievleva.movieland.entity.Genre;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.entity.Review;

import java.util.List;
import java.util.Properties;

@Service
public class MovieDaoImpl implements MovieDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Properties sqls;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Movie> getAllMovies() {
        String sqlGetMovies = sqls.getProperty("movies.select.allMovies");

        return namedParameterJdbcTemplate.query(sqlGetMovies, new MovieMapper());

    }

    @Override
    public Movie getMovieById(String movieId) {
        String sql = sqls.getProperty("movie.select.byId");

        Movie movie = namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", movieId),
                new MovieByIdMapper());

        movie.setReviews(getReviewsOfMovie(movieId));
        movie.setGenres(getGenresOfMovie(movieId));

        return movie;
    }

    private List<Review> getReviewsOfMovie(String movieId) {
        String sql = sqls.getProperty("review.select.byMovieId");

        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource("id", movieId),
                new ReviewByIdMapper());
    }

    private List<Genre> getGenresOfMovie(String movieId) {
        String sql = sqls.getProperty("genre.select.byMovieId");

        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource("id", movieId),
                new GenresByIdMapper());
    }

}
