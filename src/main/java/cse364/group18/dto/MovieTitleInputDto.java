package cse364.group18.dto;

import cse364.group18.exception.*;
import cse364.group18.model.Movie;
import cse364.group18.repository.MovieRepository;

import java.util.List;


public class MovieTitleInputDto {
    private String title;
    private final int year;
    private final int limit;
    private final MovieRepository movieRepository;

    public MovieTitleInputDto(String title, int year, int limit, MovieRepository movieRepository) {
        this.title = title;
        this.year = year;
        this.limit = limit;
        this.movieRepository = movieRepository;
        checkValidity();
    }

    public String getTitle() { return title; }

    public int getYear() { return year; }

    public int getLimit() {
        return limit;
    }

    private void checkValidity() {
        checkValidLimit(limit);
        checkValidTitle(title, year);
        checkValidYear(title, year);
    }

    private void checkValidTitle(String title, int year) throws NotFoundTitleException {
        /*
            Is there at least one movie?
         */
        List<Movie> movieList = movieRepository.findByTitleIgnoreCase(title);

        if (movieList.isEmpty()){
            throw new NotFoundTitleException("Invalid title: ", title);
        }

        if (movieList.size() > 1 && year == -1) {
            throw new NonUniqueTitleException("There are the same-titled movies: ", title);
        }
        // Change case insensitive title to DB format.
        this.title = movieList.get(0).getTitle();
    }

    private void checkValidYear(String title, int year) throws InvalidInputException, NotFoundYearException {
        // default year input value is -1
        if (year != -1 && year < 1901) {
            throw new InvalidInputException("User input 'year' should be larger 1900: ", Integer.toString(year));
        }

        if (year != -1 && movieRepository.findByTitleAndYear(title, String.valueOf(year)).isEmpty()) {
            throw new NotFoundYearException("There is no conditions-matched movie: ", title + " (" + year + ')');
        }
    }

    private void checkValidLimit(int limit) throws InvalidInputException {
        if (limit != -1 && limit < 0 || limit > 100) {
            throw new InvalidInputException("Invalid limit: ", Integer.toString(limit));
        }
    }
}
