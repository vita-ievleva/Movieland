package ua.ievleva.movieland.service;


import ua.ievleva.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<Movie> getAllMovies(Map<String, String> orderByParameters);

    Movie getMovieById(String movieId, Map<String, String> parameters);

    List<Movie> searchMovies(Map<String, String> searchParameters);

    boolean rateForMovie( Map<String, String> movieRate);

}
