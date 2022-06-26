package cse364.group18.dto;

import java.util.List;


public class MovieDto {

    private final String title;
    private final String year;
    private final List<String> genres;
    private final String imdb;
    private final String poster;

    public MovieDto(String title, String year, List<String> genres, String imdb, String poster) {
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.imdb = imdb;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getImdb() {
        return imdb;
    }

    public String getPoster() {
        return poster;
    }
}