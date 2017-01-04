package ua.ievleva.movieland.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.ievleva.movieland.dao.ReviewDao;
import ua.ievleva.movieland.security.annotation.RequiresUserType;
import ua.ievleva.movieland.service.ReviewService;

import java.util.Map;

import static ua.ievleva.movieland.entity.Role.ROLE_USER;

@Component
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @RequiresUserType(ROLE_USER)
    @Override
    public boolean addReview(Map<String, String> review) {
        return reviewDao.addReview(review);
    }

    @RequiresUserType(ROLE_USER)
    @Override
    public boolean deleteReview(Map<String, String> review) {
        return reviewDao.deleteReview(review);
    }
}
