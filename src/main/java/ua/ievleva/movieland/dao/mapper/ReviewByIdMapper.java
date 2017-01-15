package ua.ievleva.movieland.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ua.ievleva.movieland.entity.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ReviewByIdMapper implements ResultSetExtractor<Collection<Review>> {

    @Override
    public Collection<Review> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Review> reviewList = new ArrayList<>();

        while (rs.next()) {
            reviewList.add(new Review(rs.getString("review_text")));
        }
        return reviewList;
    }
}
