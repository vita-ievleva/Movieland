package ua.ievleva.movieland.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ievleva.movieland.dao.ReviewDao;
import ua.ievleva.movieland.service.ReviewService;

import java.util.Map;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public boolean addReview(Map<String, String> review) {
        return reviewDao.addReview(review);
    }

    @Override
    public boolean deleteReview(Map<String, String> review) {
        return reviewDao.deleteReview(review);
    }
}
