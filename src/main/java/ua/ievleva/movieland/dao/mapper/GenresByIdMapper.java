package ua.ievleva.movieland.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ua.ievleva.movieland.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenresByIdMapper implements ResultSetExtractor<Collection<Genre>> {

    @Override
    public Collection<Genre> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Genre> genreList = new ArrayList<>();

        while (rs.next()) {
            genreList.add(new Genre(rs.getString("genre")));
        }
        return genreList;
    }
}
