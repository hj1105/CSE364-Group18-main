package cse364.group18.model;

import javax.persistence.ElementCollection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document
@Getter
@Setter
public class Movie {

    private @JsonIgnore Long movieId;
    private String title;
    private @JsonIgnore String year;
    private @JsonIgnore String rated;
    private @JsonIgnore String released;
    private @JsonIgnore String runtime;
    private @JsonIgnore @ElementCollection List<String> genres = new ArrayList<>();
    private @JsonIgnore String director;
    private @JsonIgnore String writer;
    private @JsonIgnore @ElementCollection List<String> actors = new ArrayList<>();
    private @JsonIgnore String plot;
    private @JsonIgnore String language;
    private @JsonIgnore String country;
    private @JsonIgnore String poster;
    private @JsonIgnore String imdbRating;
    private @JsonIgnore String imdbVotes;
    private @JsonIgnore String imdbId;

    public Movie() {
    }

    public Movie(Long movieId, String title, String year, String rated, String released, String runtime, List<String> genres, String director, String writer, List<String> actors, String plot, String language, String country, String poster, String imdbRating, String imdbVotes, String imdbId) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genres = genres;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.poster = poster;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.imdbId = imdbId;
    }
}