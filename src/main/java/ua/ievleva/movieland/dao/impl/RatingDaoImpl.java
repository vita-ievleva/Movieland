package ua.ievleva.movieland.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.ievleva.movieland.dao.RatingDao;

import java.util.Map;
import java.util.Properties;


@Repository
public class RatingDaoImpl implements RatingDao {

    @Autowired
    private Properties sqlQueries;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public boolean rateForMovie(Map<String, String> movieRate) {

        // {"movieId":"0", "username":"", "rate":"9"}


        // insert or update rate for movie by ID
        // recalculate rate for movie and update movie table
        return false;
    }
}
