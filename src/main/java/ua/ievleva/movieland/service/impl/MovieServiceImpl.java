package ua.ievleva.movieland.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.ievleva.movieland.dao.MovieDao;
import ua.ievleva.movieland.dao.RatingDao;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.service.MovieService;

import java.util.List;
import java.util.Map;

@Component
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private RatingDao ratingDao;


    @Override
    public List<Movie> getAllMovies(Map<String, String> orderByParameters) {
        return movieDao.getAllMovies(orderByParameters);
    }

    @Override
    public Movie getMovieById(String movieId, Map<String, String> parameters) {
        return movieDao.getMovieById(movieId, parameters);
    }

    @Override
    public List<Movie> searchMovies(Map<String, String> searchParameters) {
        return movieDao.searchMovies(searchParameters);
    }

    @Override
    public boolean rateForMovie( Map<String, String> movieRate) {

        return false;
    }
}
