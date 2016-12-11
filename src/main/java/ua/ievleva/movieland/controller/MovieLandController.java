package ua.ievleva.movieland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ievleva.movieland.dao.MovieDao;

import java.io.IOException;
import java.util.Map;


@RestController
public class MovieLandController {

    private final Logger logger = LoggerFactory.getLogger(MovieLandController.class);


    @Autowired
    private MovieDao movieDao;

    @Autowired
    private ObjectMapper jsonMapper;


    @GetMapping(value = "/v1/movies", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAllMovies() {

        return new ResponseEntity<>(movieDao.getAllMovies(), HttpStatus.OK);
    }


    @GetMapping(value = "/v1/movie/{movieId}", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getMovieById(@PathVariable("movieId") String movieId) {

        return new ResponseEntity<>(movieDao.getMovieById(movieId), HttpStatus.OK);
    }

    @GetMapping(value = "/v1/search", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> searchMovies(@RequestBody String searchQuery) {

        Map<String, String> searchParameters = null;

        try {
             searchParameters = jsonMapper.readValue(searchQuery, Map.class);
        } catch (IOException e) {
            logger.error("Search query is invalid", e);
        }

        return new ResponseEntity<>(movieDao.searchMovies(searchParameters), HttpStatus.FOUND);

    }

}
