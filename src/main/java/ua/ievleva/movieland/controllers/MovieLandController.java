package ua.ievleva.movieland.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.service.MovieService;

import java.util.Collection;
import java.util.Map;

@RequestMapping("/v1")
@RestController("movieLandController")
public class MovieLandController {

    private final MovieService movieService;

    @Autowired
    public MovieLandController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/movies", produces = {"application/json", "application/xml"})
    public ResponseEntity<Collection<Movie>> getAllMovies(@RequestParam(required = false) Map<String, String> parameters) {

        return new ResponseEntity<>(movieService.getAllMovies(parameters), HttpStatus.OK);
    }

    @GetMapping(value = "/movie/{movieId}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Movie> getMovieById(@PathVariable("movieId") String movieId,
                                          @RequestParam(required = false) Map<String, String> parameters) {

        return new ResponseEntity<>(movieService.getMovieById(movieId, parameters), HttpStatus.OK);
    }

    @PostMapping(value = "/search", consumes = "application/json", produces = {"application/json", "application/xml"})
    public ResponseEntity<Collection<Movie>> searchMovies(@RequestBody Map<String, String> searchQuery) {

        return new ResponseEntity<>(movieService.searchMovies(searchQuery), HttpStatus.OK);
    }

}
