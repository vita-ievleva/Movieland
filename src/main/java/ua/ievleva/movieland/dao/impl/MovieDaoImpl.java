package ua.ievleva.movieland.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ua.ievleva.movieland.dao.MovieDao;
import ua.ievleva.movieland.dao.mapper.*;
import ua.ievleva.movieland.entity.Genre;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.entity.Review;

import java.util.List;
import java.util.Map;
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
        String sql = sqls.getProperty("movies.select.allMovies");

        return namedParameterJdbcTemplate.query(sql, new MovieMapper());

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


    @Override
    public List<Movie> searchMovies(Map<String, String> searchParameters) {
        String sql = sqls.getProperty("movies.select.bySearchParameters");
        sql = sql.replace("[conditions]", buildConditionQuery(searchParameters));

        return namedParameterJdbcTemplate.query(sql, new MovieMapper());
    }

    private String buildConditionQuery(Map<String, String> searchParameters) {

        StringBuilder sb = new StringBuilder();

        searchParameters.forEach((k, v) -> {
            sb.append(k).append("=\"").append(v).append("\" AND ");

        });

        return sb.delete(sb.length() - 4, sb.length() - 1).toString();
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
