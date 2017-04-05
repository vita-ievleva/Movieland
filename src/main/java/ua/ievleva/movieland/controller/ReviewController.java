package ua.ievleva.movieland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ievleva.movieland.security.token.TokenUtils;
import ua.ievleva.movieland.service.ReviewService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/v1")
@RestController
public class ReviewController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/review", consumes = "application/json", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> addReview(@RequestBody Map<String, String> review, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        review.put("username", tokenUtils.parseToken(token).getSubject());

        Map<String, Boolean> result = new HashMap<>();
        result.put("result", reviewService.addReview(review));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/review")
    public ResponseEntity<?> deleteReview(@RequestBody Map<String, String> review, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        review.put("username", tokenUtils.parseToken(token).getSubject());

        return new ResponseEntity<>(reviewService.deleteReview(review),HttpStatus.OK);
    }

}
