package cse364.group18.service;

import cse364.group18.dto.MovieTitleInputDto;
import cse364.group18.exception.NonUniqueTitleException;
import cse364.group18.exception.NotFoundException;
import cse364.group18.exception.NotFoundTitleException;
import cse364.group18.model.Rating;
import cse364.group18.model.Movie;
import cse364.group18.repository.*;
import cse364.group18.service.core.MovieWeightedScorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class MovieTitleBasedRecommender {

    private final RatingRepositoryAdapter ratingRepositoryAdapter;
    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieTitleBasedRecommender(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.ratingRepositoryAdapter = new RatingRepositoryAdapter(ratingRepository);
    }

    private Long findMovieId(String title, int year){
        Long movieId;
        if (year == -1) {
            List<Movie> movie = movieRepository.findByTitleIgnoreCase(title);
            if (movie.isEmpty()) {
                throw new NotFoundTitleException("Not found title: ", title);
            } else if (movie.size() > 1) {
                throw new NonUniqueTitleException("Non-unique title: ", title);
            } else {
                movieId = movie.get(0).getMovieId();
            }
        } else {
            Optional<Movie> movieOpt = movieRepository.findByTitleAndYear(title, Integer.toString(year));
            if (movieOpt.isEmpty()) {
                throw new NotFoundException("Failed to find a matched movie: ", title);
            } else {
                movieId = movieOpt.get().getMovieId();
            }
        }
        return movieId;
    }

    public HashMap<Long, Double> getMovieScoreMap(MovieTitleInputDto inputDto) {
        HashMap<Long, MovieWeightedScorer> movieScoreMap = new HashMap<>();
        HashMap<Long, Double> flattenedMovieScoreMap = new HashMap<>();

        String title = inputDto.getTitle();
        int year = inputDto.getYear();

        Long movieId = findMovieId(title, year);
        List<Rating> moviesRatingList = ratingRepository.findByMovieId(movieId);

        for (Rating rating : moviesRatingList) {
            Long userId = rating.getUserId();
            List<Rating> usersRatingList = ratingRepositoryAdapter.findByUserId(userId);

            double weight = rating.getScore() / 5.0;

            for (Rating userRating : usersRatingList) {
                Long mId = userRating.getMovieId();

                // 찾는 영화 mId 가 movieScore 안에 없다
                if (!movieScoreMap.containsKey(mId)) {
                    // movieScore 안에 추가
                    movieScoreMap.put(mId, new MovieWeightedScorer(userRating.getScore() * weight, 1));
                } else {
                    // 영화 mId가 이미 movieScore 안에 존재한다
                    // mId 에 대응하는 value 를 가져와서
                    MovieWeightedScorer scorer = movieScoreMap.get(mId);
                    scorer.updateRecord(userRating.getScore() * weight);
                }
            }
        }

        for (Map.Entry<Long, MovieWeightedScorer> entry: movieScoreMap.entrySet()) {
            Long mid = entry.getKey();
            Double avgScore = entry.getValue().getAverage();
            flattenedMovieScoreMap.put(mid, avgScore);
        }

        return flattenedMovieScoreMap;
    }
}
