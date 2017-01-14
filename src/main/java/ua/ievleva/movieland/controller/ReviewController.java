package ua.ievleva.movieland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.ievleva.movieland.security.token.TokenUtils;
import ua.ievleva.movieland.service.ReviewService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class ReviewController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/v1/review")
    public ResponseEntity<?> addReview(@RequestBody Map<String, String> review, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        review.put("username", tokenUtils.parseToken(token).getSubject());

        return new ResponseEntity<>(reviewService.addReview(review),HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/review")
    public ResponseEntity<?> deleteReview(@RequestBody Map<String, String> review, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        review.put("username", tokenUtils.parseToken(token).getSubject());

        return new ResponseEntity<>(reviewService.deleteReview(review),HttpStatus.OK);
    }

}
