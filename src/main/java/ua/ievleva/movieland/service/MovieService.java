package ua.ievleva.movieland.service;


import ua.ievleva.movieland.entity.Movie;

import java.util.Collection;
import java.util.Map;

public interface MovieService {

    Collection<Movie> getAllMovies(Map<String, String> orderByParameters);

    Movie getMovieById(String movieId, Map<String, String> parameters);

    Collection<Movie> searchMovies(Map<String, String> searchParameters);

    boolean rateForMovie( Map<String, String> movieRate);

}
