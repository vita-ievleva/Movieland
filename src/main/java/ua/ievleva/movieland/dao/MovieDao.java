package ua.ievleva.movieland.dao;

import ua.ievleva.movieland.entity.Movie;

import java.util.List;


public interface MovieDao {

    List<Movie> getAllMovies();

    Movie getMovieById(String movieId);
}
