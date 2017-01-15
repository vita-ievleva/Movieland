package ua.ievleva.movieland.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.ievleva.movieland.dao.MovieDao;
import ua.ievleva.movieland.dao.mapper.GenresByIdMapper;
import ua.ievleva.movieland.dao.mapper.MovieByIdMapper;
import ua.ievleva.movieland.dao.mapper.MovieMapper;
import ua.ievleva.movieland.dao.mapper.ReviewByIdMapper;
import ua.ievleva.movieland.dao.util.QueryGenerator;
import ua.ievleva.movieland.entity.Genre;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.entity.Review;

import java.util.Collection;
import java.util.Map;


@PropertySource("classpath:movieland.properties")
@Repository
public class MovieDaoImpl implements MovieDao {

    private static final MovieMapper MOVIE_MAPPER = new MovieMapper();
    private static final MovieByIdMapper MOVIE_BY_ID_MAPPER = new MovieByIdMapper();
    private static final ReviewByIdMapper REVIEW_BY_ID_MAPPER = new ReviewByIdMapper();
    private static final GenresByIdMapper GENRES_BY_ID_MAPPER = new GenresByIdMapper();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private QueryGenerator queryGenerator;

    @Override
    public Collection<Movie> getAllMovies(Map<String, String> parameters) {
        return namedParameterJdbcTemplate.query(
                queryGenerator.generateQueryAllMovies(parameters, "movies.select.allMovies"), MOVIE_MAPPER);
    }

    @Override
    public Movie getMovieById(String movieId, Map<String, String> parameters) {
        String sql = queryGenerator.generateQuery("movie.select.byId");

        Movie movie = namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", movieId),
                MOVIE_BY_ID_MAPPER);

        movie.setReviews(getReviewsByMovieId(movieId));
        movie.setGenres(getGenresByMovieId(movieId));

        return movie;
    }

    @Override
    public Collection<Movie> searchMovies(Map<String, String> searchParameters) {
        String sql = queryGenerator.generateSearchQuery(searchParameters);

        return namedParameterJdbcTemplate.query(sql, MOVIE_MAPPER);
    }

    private Collection<Review> getReviewsByMovieId(String movieId) {
        String sql = queryGenerator.generateQuery("review.select.byMovieId");

        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource("id", movieId),
                REVIEW_BY_ID_MAPPER);
    }

    private Collection<Genre> getGenresByMovieId(String movieId) {
        String sql = queryGenerator.generateQuery("genre.select.byMovieId");

        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource("id", movieId),
                GENRES_BY_ID_MAPPER);
    }

}
