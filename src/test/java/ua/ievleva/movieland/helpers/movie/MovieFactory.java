package ua.ievleva.movieland.helpers.movie;


import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.helpers.MovielandTestConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MovieFactory {

    public static final Movie DEFAULT_MOVIE = aDefaultMovie();
    public static final Map<String, String> EMPTY_MAP = new HashMap<>();
    public static final Map<String, String> INVALID_CREDENTIALS_MAP = new HashMap<>();
    public static final Collection<Movie> DEFAULT_MOVIE_LIST;

    static {
        DEFAULT_MOVIE_LIST = new ArrayList<>();
        DEFAULT_MOVIE_LIST.add(DEFAULT_MOVIE);
        DEFAULT_MOVIE_LIST.add(DEFAULT_MOVIE);

        INVALID_CREDENTIALS_MAP.put("username", "invalid");
    }


    private static Movie aDefaultMovie(){
        return MovieBuilder.aMovie().id(Long.valueOf(MovielandTestConstants.ID))
                .title(MovielandTestConstants.DEFAULT_MOVIE_NAME)
                .yearOfRelease(MovielandTestConstants.YEAR_OF_RELEASE).build();
    }




}
