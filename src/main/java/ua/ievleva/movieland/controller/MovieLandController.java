package ua.ievleva.movieland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ievleva.movieland.dao.MovieDao;

import java.util.Map;

@RestController
public class MovieLandController {

    @Autowired
    private MovieDao movieDao;

    @GetMapping(value = "/v1/movies", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAllMovies(@RequestParam(required = false) Map<String, String> orderBy) {

        return new ResponseEntity<>(movieDao.getAllMovies(orderBy), HttpStatus.OK);
    }

    @GetMapping(value = "/v1/movie/{movieId}", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getMovieById(@PathVariable("movieId") String movieId) {

        return new ResponseEntity<>(movieDao.getMovieById(movieId), HttpStatus.OK);
    }

    @PostMapping(value = "/v1/search", consumes = "application/json", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> searchMovies(@RequestBody Map<String, String> searchQuery) {

        return new ResponseEntity<>(movieDao.searchMovies(searchQuery), HttpStatus.OK);
    }
}
