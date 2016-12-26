package ua.ievleva.movieland.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ua.ievleva.movieland.dao.MovieDao;
import ua.ievleva.movieland.dao.mapper.GenresByIdMapper;
import ua.ievleva.movieland.dao.mapper.MovieByIdMapper;
import ua.ievleva.movieland.dao.mapper.MovieMapper;
import ua.ievleva.movieland.dao.mapper.ReviewByIdMapper;
import ua.ievleva.movieland.entity.Genre;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.entity.Review;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static ua.ievleva.movieland.dao.util.QueryBuilderUtil.buildConditionQuery;

@Component
public class MovieDaoImpl implements MovieDao {

    private static final MovieMapper MOVIE_MAPPER = new MovieMapper();
    private static final MovieByIdMapper MOVIE_BY_ID_MAPPER = new MovieByIdMapper();
    private static final ReviewByIdMapper REVIEW_BY_ID_MAPPER = new ReviewByIdMapper();
    private static final GenresByIdMapper GENRES_BY_ID_MAPPER = new GenresByIdMapper();

    @Autowired
    private Properties sqlQueries;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Movie> getAllMovies() {
        String sql = sqlQueries.getProperty("movies.select.allMovies");

        return namedParameterJdbcTemplate.query(sql, MOVIE_MAPPER);
    }

    @Override
    public Movie getMovieById(String movieId) {
        String sql = sqlQueries.getProperty("movie.select.byId");

        Movie movie = namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", movieId),
                MOVIE_BY_ID_MAPPER);

        movie.setReviews(getReviewsByMovieId(movieId));
        movie.setGenres(getGenresByMovieId(movieId));

        return movie;
    }

    @Override
    public List<Movie> searchMovies(Map<String, String> searchParameters) {
        String sql = sqlQueries.getProperty("movies.select.bySearchParameters");
        sql = sql.replace("[conditions]", buildConditionQuery(searchParameters));

        return namedParameterJdbcTemplate.query(sql, MOVIE_MAPPER);
    }

    private List<Review> getReviewsByMovieId(String movieId) {
        String sql = sqlQueries.getProperty("review.select.byMovieId");

        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource("id", movieId),
                REVIEW_BY_ID_MAPPER);
    }

    private List<Genre> getGenresByMovieId(String movieId) {
        String sql = sqlQueries.getProperty("genre.select.byMovieId");

        return namedParameterJdbcTemplate.query(sql,
                new MapSqlParameterSource("id", movieId),
                GENRES_BY_ID_MAPPER);
    }

}
