package ua.ievleva.movieland.helpers.movie;


import ua.ievleva.movieland.entity.Genre;
import ua.ievleva.movieland.entity.Movie;
import ua.ievleva.movieland.entity.Review;

import java.util.Collection;

public class MovieBuilder {
    private Long id;
    private Double price;
    private String title;
    private String yearOfRelease;
    private String description;
    private String country;
    private Collection<Genre> genres;
    private Collection<Review> reviews;
    private Double rating;

    private MovieBuilder() {}

    public static MovieBuilder aMovie() {
        return new MovieBuilder();
    }

    public MovieBuilder id(Long id){
        this.id = id;
        return this;
    }

    public MovieBuilder title(String title){
        this.title = title;
        return this;
    }

    public MovieBuilder yearOfRelease(String yearOfRelease){
        this.yearOfRelease = yearOfRelease;
        return this;
    }

    public Movie build() {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setYearOfRelease(yearOfRelease);
        return movie;
    }

}
