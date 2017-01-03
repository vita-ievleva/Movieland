package ua.ievleva.movieland.dao;


import java.util.Map;

public interface ReviewDao {

    boolean addReview(Map<String, String> review);
}
