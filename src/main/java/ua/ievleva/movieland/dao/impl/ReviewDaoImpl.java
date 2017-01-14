package ua.ievleva.movieland.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.ievleva.movieland.dao.ReviewDao;
import ua.ievleva.movieland.security.annotation.RequiresUserType;

import java.util.Map;
import java.util.Properties;

import static ua.ievleva.movieland.entity.Role.ROLE_USER;


@Repository
public class ReviewDaoImpl implements ReviewDao {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private Properties sqlQueries;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean addReview(Map<String, String> review) {
        String sql = sqlQueries.getProperty("insert.review");

        return updateReview(review, sql);
    }

    @RequiresUserType(ROLE_USER)
    @Override
    public boolean deleteReview(Map<String, String> review) {
        String sql = sqlQueries.getProperty("delete.review");

        return updateReview(review, sql);
    }


    private boolean updateReview(Map<String, String> review, String sql) {
        if (review.size() == 3) {
            int updatedRow = namedParameterJdbcTemplate.update(sql, review);

            logger.debug("Updated " + updatedRow + " reviews.");
            return updatedRow == 1;
        }

        logger.error("Invalid parameters were passed.");
        return false;

    }
}
