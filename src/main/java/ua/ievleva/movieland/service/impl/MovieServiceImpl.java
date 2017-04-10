package ua.ievleva.movieland.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ievleva.movieland.dao.MovieDao;
import ua.ievleva.movieland.dao.RatingDao;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.service.MovieService;

import java.util.Collection;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private CurrencyServiceImpl currencyService;


    @Override
    public Collection<Movie> getAllMovies(Map<String, String> parameters) {
        Collection<Movie> movies = movieDao.getAllMovies(parameters);
        if (parameters.containsKey("currency")) {
            Double rate = currencyService.getCurrencyRate(parameters.get("currency"));
            movies.forEach(m -> m.setPrice(m.getPrice() / rate));
        }
        return movies;
    }

    @Override
    public Movie getMovieById(String movieId, Map<String, String> parameters) {
        Movie movie = movieDao.getMovieById(movieId, parameters);

        if (parameters.containsKey("currency")) {
            Double rate = currencyService.getCurrencyRate(parameters.get("currency"));
            movie.setPrice(movie.getPrice() / rate);
        }

        return movie;
    }

    @Override
    public Collection<Movie> searchMovies(Map<String, String> searchParameters) {
        return movieDao.searchMovies(searchParameters);
    }

    @Override
    public boolean rateForMovie( Map<String, String> movieRate) {

        return false;
    }
}
