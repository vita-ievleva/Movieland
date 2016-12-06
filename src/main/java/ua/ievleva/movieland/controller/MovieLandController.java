package ua.ievleva.movieland.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.ievleva.movieland.dao.impl.MovieDaoImpl;


@RestController
public class MovieLandController {

    private final Logger logger = LoggerFactory.getLogger(MovieLandController.class);


    @Autowired
    private MovieDaoImpl movieDao;


    @GetMapping(value = "/v1/movies", produces = "application/json")
    public ResponseEntity<?> greeting() {

        return new ResponseEntity<>(movieDao.getAllMovies(), HttpStatus.OK);
    }

}
