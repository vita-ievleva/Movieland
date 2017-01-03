package ua.ievleva.movieland.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ua.ievleva.movieland.dao.ReviewDao;
import ua.ievleva.movieland.security.annotation.RequiresUserType;

import java.util.Map;
import java.util.Properties;

import static ua.ievleva.movieland.entity.Role.ROLE_USER;


@Component
public class ReviewDaoImpl implements ReviewDao {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private Properties sqlQueries;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @RequiresUserType(ROLE_USER)
    @Override
    public boolean addReview(Map<String, String> review) {
        String sql = sqlQueries.getProperty("insert.review");

        if (review.size() == 3) {
            int insertedRow = namedParameterJdbcTemplate.update(sql, review);

            logger.debug("Inserted " + insertedRow + "new review.");
            return insertedRow == 1;
        }

        logger.error("Review has not been added: Invalid parameters were passed.");
        return false;
    }

}
