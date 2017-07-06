package ua.ievleva.movieland.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.service.MovieService;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static ua.ievleva.movieland.helpers.MovielandTestConstants.ID;
import static ua.ievleva.movieland.helpers.movie.MovieFactory.*;

public class MovieLandControllerTest {
    private MovieLandController movieLandControllerSUT;
    private MovieService movieServiceMock = mock(MovieService.class);;

    @Before
    public void setUp() throws Exception {
        movieLandControllerSUT = new MovieLandController(movieServiceMock);

        when(movieServiceMock.getAllMovies(EMPTY_MAP)).thenReturn(DEFAULT_MOVIE_LIST);
        when(movieServiceMock.getMovieById(ID, EMPTY_MAP)).thenReturn(DEFAULT_MOVIE);
        when(movieServiceMock.searchMovies(EMPTY_MAP)).thenReturn(DEFAULT_MOVIE_LIST);
    }

    @Test
    public void shouldFetchListOfAllMovies() throws Exception {
        ResponseEntity<Collection<Movie>> result = movieLandControllerSUT.getAllMovies(EMPTY_MAP);

        assertThat("Response should be 200 OK.", result.getStatusCode().is2xxSuccessful());
        assertThat("Response should be equal to default movies list.",  result.getBody(), is(DEFAULT_MOVIE_LIST));

        verify(movieServiceMock, times(1)).getAllMovies(EMPTY_MAP);
    }

    @Test
    public void getMovieById() throws Exception {
        ResponseEntity<Movie> result = movieLandControllerSUT.getMovieById(ID, EMPTY_MAP);

        assertThat("Response should be 200 OK.", result.getStatusCode().is2xxSuccessful());
        assertThat(result.getBody(), is(DEFAULT_MOVIE));

        verify(movieServiceMock, times(1)).getMovieById(ID, EMPTY_MAP);
    }

    @Test
    public void searchMovies() throws Exception {
        ResponseEntity<Collection<Movie>> result = movieLandControllerSUT.searchMovies(EMPTY_MAP);

        assertThat("Response should be 200 OK.", result.getStatusCode().is2xxSuccessful());
        assertThat(result.getBody(), is(DEFAULT_MOVIE_LIST));

        verify(movieServiceMock, times(1)).searchMovies(EMPTY_MAP);
    }

}