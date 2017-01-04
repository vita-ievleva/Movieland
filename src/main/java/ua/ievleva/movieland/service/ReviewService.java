package ua.ievleva.movieland.service;


import java.util.Map;


public interface ReviewService {

    boolean addReview(Map<String, String> review);

    boolean deleteReview(Map<String, String> review);
}
