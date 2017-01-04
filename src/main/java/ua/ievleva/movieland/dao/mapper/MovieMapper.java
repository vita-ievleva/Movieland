package ua.ievleva.movieland.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ua.ievleva.movieland.entity.Genre;
import ua.ievleva.movieland.entity.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MovieMapper implements ResultSetExtractor<List<Movie>> {


    @Override
    public List<Movie> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Movie> map = new HashMap<>();
        Movie movie;

        while (rs.next()) {
            List<Genre> genres;
            Long id = rs.getLong("id");

            movie = map.get(id);

            if (movie == null) {
                movie = new Movie();
                genres = new ArrayList<>();

                movie.setId(id);
                movie.setTitle(rs.getString("name"));
                movie.setYearOfRelease(rs.getString("year"));
                movie.setRating(rs.getDouble("rate"));
                movie.setPrice(Double.valueOf(rs.getString("price")));

                genres.add(new Genre(rs.getString("genre")));
                movie.setGenres(genres);

                map.put(id, movie);
            } else {
                genres = movie.getGenres();
                movie.getGenres().add(new Genre(rs.getString("genre")));
                movie.setGenres(genres);
            }
        }
        return new ArrayList<>(map.values());
    }

}
