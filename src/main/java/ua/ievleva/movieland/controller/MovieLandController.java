package ua.ievleva.movieland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ievleva.movieland.service.MovieService;

import java.util.Map;

@RestController
public class MovieLandController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/v1/movies", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAllMovies(@RequestParam(required = false) Map<String, String> parameters) {

        return new ResponseEntity<>(movieService.getAllMovies(parameters), HttpStatus.OK);
    }

    @GetMapping(value = "/v1/movie/{movieId}", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getMovieById(@PathVariable("movieId") String movieId,
                                          @RequestParam(required = false) Map<String, String> parameters) {

        return new ResponseEntity<>(movieService.getMovieById(movieId, parameters), HttpStatus.OK);
    }

    @PostMapping(value = "/v1/search", consumes = "application/json", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> searchMovies(@RequestBody Map<String, String> searchQuery) {

        return new ResponseEntity<>(movieService.searchMovies(searchQuery), HttpStatus.OK);
    }

    @PostMapping(value = "/v1/rate", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> rateForMovie(@RequestBody Map<String, String> movieRate) {

        return new ResponseEntity<>(movieService.rateForMovie(movieRate), HttpStatus.OK);
    }
}
