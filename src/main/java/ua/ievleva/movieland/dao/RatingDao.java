package ua.ievleva.movieland.dao;


import java.util.Map;

public interface RatingDao {

    boolean rateForMovie(Map<String, String> movieRate);
}
