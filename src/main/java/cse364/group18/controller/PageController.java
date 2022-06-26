package cse364.group18.controller;

import cse364.group18.dto.*;
import cse364.group18.exception.*;
import cse364.group18.model.Movie;
import cse364.group18.repository.MovieRepository;
import cse364.group18.service.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
public class PageController {

    private final List<String> GENRES = new ArrayList<>(Arrays.asList(
            "", "Action", "Animation", "Crime", "Drama",
            "Horror", "Romance", "Sci-Fi", "Thriller", "War"
    ));

    private final MovieRepository movieRepository;
    private final RecommendationService recommendationService;

    @Autowired
    public PageController(MovieRepository movieRepository, RecommendationService recommendationService) {
        this.movieRepository = movieRepository;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/index.html")
    public ModelAndView index(Model model) {
        List<List<MovieDto>> movies = new ArrayList<>();

        try {
            for (String genre : GENRES) {
                UserProfileInputDto inputDto = new UserProfileInputDto("", "", "", genre);
                List<MovieDto> movie = recommendationService.recommendByUserProfile(inputDto);
                movies.add(movie);
            }
            model.addAttribute("genreNames", GENRES);
            model.addAttribute("movieList", movies);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @GetMapping("/users/recommendations.html")
    public ModelAndView recommendByUser(
            @RequestParam(value="gender", required=false) String gender,
            @RequestParam(value="age", required=false) String age,
            @RequestParam(value="occupation", required=false) String occupation,
            @RequestParam(value="genres", required=false) String genres,
            Model model) {

        List<MovieDto> movies;

        try {
            UserProfileInputDto inputDto = new UserProfileInputDto(gender, age, occupation, genres);
            movies = recommendationService.recommendByUserProfile(inputDto);
            model.addAttribute("movies", movies);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/users/recommendations.html");
        return modelAndView;
    }

    @GetMapping("/movies/recommendations.html")
    public ModelAndView recommendByTitle(
            @RequestParam(value="title") String title,
            @RequestParam(value="year", required=false, defaultValue="-1") int year,
            @RequestParam(value="limit", required=false, defaultValue="10") int limit,
            Model model) {

        List<MovieDto> movies = new ArrayList<>();

        try {
            MovieTitleInputDto inputDto = new MovieTitleInputDto(title, year, limit, movieRepository);
            movies = recommendationService.recommendByMovieTitle(inputDto);
            Movie movie = movieRepository.findByTitleIgnoreCase(title).get(0);
            model.addAttribute("movies", movies);
            model.addAttribute("title", movie.getTitle());
            if (year == -1) year = Integer.parseInt(movie.getYear());
            model.addAttribute("year", year);
            model.addAttribute("limit", limit);
        } catch (NonUniqueTitleException e) {
            // There are more than two movies that satisfy the title & year condition.
            List<Movie> nonUnique = movieRepository.findByTitleIgnoreCase(title);
            for (Movie movie : nonUnique) {
                movies.add(new MovieDto(movie.getTitle(), movie.getYear(), movie.getGenres(), movie.getImdbId(), movie.getPoster()));
            }
            model.addAttribute("movies", movies);
            model.addAttribute("title", title);
            model.addAttribute("year", year);
            model.addAttribute("limit", limit);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/movies/choose.html");
            return modelAndView;
        } catch (NotFoundTitleException e) {
            // There is no title matched movie.
            e.printStackTrace();
            model.addAttribute("title", title);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/movies/title_again.html");
            return modelAndView;
        } catch (NotFoundYearException e) {
            // There is title matched movie but no year matched movie.
            e.printStackTrace();
            List<Movie> nonUnique = movieRepository.findByTitleIgnoreCase(title);
            for (Movie movie : nonUnique) {
                movies.add(new MovieDto(movie.getTitle(), movie.getYear(), movie.getGenres(), movie.getImdbId(), movie.getPoster()));
            }
            model.addAttribute("movies", movies);
            model.addAttribute("title", title);
            model.addAttribute("year", year);
            model.addAttribute("limit", limit);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/movies/wrong_year.html");
            return modelAndView;
        } catch (InvalidInputException e) {
            // limit or year value is out of proper range.
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/movies/recommendations.html");
        return modelAndView;
    }

    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }
}