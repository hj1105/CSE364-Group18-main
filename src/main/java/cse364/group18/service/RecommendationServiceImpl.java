package cse364.group18.service;

import cse364.group18.dto.MovieDto;
import cse364.group18.dto.MovieTitleInputDto;
import cse364.group18.dto.UserProfileInputDto;
import cse364.group18.exception.NotFoundException;
import cse364.group18.model.Movie;
import cse364.group18.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RecommendationServiceImpl implements RecommendationService {

    MovieRepository movieRepository;
    UserProfileBasedRecommender userRecommender;
    MovieTitleBasedRecommender titleRecommender;

    @Autowired
    RecommendationServiceImpl(MovieRepository movieRepository, UserProfileBasedRecommender userProfileBasedRecommender, MovieTitleBasedRecommender movieTitleBasedRecommender) {
        this.movieRepository = movieRepository;
        this.userRecommender = userProfileBasedRecommender;
        this.titleRecommender = movieTitleBasedRecommender;
    }

    public List<MovieDto> recommendByMovieTitle(MovieTitleInputDto inputDto) {
        HashMap<Long, Double> movieScoreMap = titleRecommender.getMovieScoreMap(inputDto);
        List<Long> sortedMovieIdList = sortByValue(movieScoreMap);
        return topNMovieDtoList(sortedMovieIdList, inputDto.getLimit());
    }

    public List<MovieDto> recommendByUserProfile(UserProfileInputDto inputDto) {
        HashMap<Long, Double> movieScoreMap = userRecommender.getMovieScoreMap(inputDto);
        List<Long> sortedMovieIdList = sortByValue(movieScoreMap);
        return topNMovieDtoList(sortedMovieIdList,10);
    }

    private MovieDto createMoviePosterOutputDto(Movie movie) throws NotFoundException {
        /*
            If there is no movie poster, null value is returned.
         */
        String imdbId = movie.getImdbId();
        String link = "http://www.imdb.com/title/" + imdbId;
        String poster;
        try {
            poster = movie.getPoster();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new NotFoundException("Not found movie poster: ", movie.getTitle());
        }
        return new MovieDto(movie.getTitle(), movie.getYear(), movie.getGenres(), link, poster);
    }

    private List<MovieDto> topNMovieDtoList(List<Long> sortedMovieIdList, int limit) {
        List<MovieDto> movieDtoList = new ArrayList<>();
        MovieDto outputDto;
        int count = 0;

        for (Long movieId : sortedMovieIdList) {
            Movie movie = movieRepository.findByMovieId(movieId);

            // Filter out less popular movie
            if (Integer.parseInt(movie.getImdbVotes()) < 100000) {
                continue;
            }

            // There's chance to fail finding the movie poster.
            try {
                outputDto = createMoviePosterOutputDto(movie);
            } catch (NotFoundException e) {
                e.printStackTrace();
                continue;
            }

            movieDtoList.add(outputDto);
            count++;

            if (count == limit)
                break;
        }

        return movieDtoList;
    }

    public static List<Long> sortByValue(final Map<Long, Double> map) {
        /*
            Sort by value in descending order.
         */
        List<Long> list = new ArrayList<>(map.keySet());

        list.sort((Comparator<? super Long>) (o1, o2) -> {
            Double v1 = map.get(o1);
            Double v2 = map.get(o2);
            return (v2).compareTo(v1);
        });

        return list;
    }
}
