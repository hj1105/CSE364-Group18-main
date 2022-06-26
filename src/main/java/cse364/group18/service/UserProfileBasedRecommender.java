package cse364.group18.service;

import cse364.group18.dto.UserProfileInputDto;
import cse364.group18.model.User;
import cse364.group18.model.Rating;
import cse364.group18.model.Movie;
import cse364.group18.repository.MovieRepository;
import cse364.group18.repository.RatingRepository;
import cse364.group18.repository.RatingRepositoryAdapter;
import cse364.group18.repository.UserRepository;
import cse364.group18.service.core.UserSimilarityScorer;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserProfileBasedRecommender {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RatingRepositoryAdapter ratingRepositoryAdapter;

    public UserProfileBasedRecommender(MovieRepository movieRepository, UserRepository userRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.ratingRepositoryAdapter = new RatingRepositoryAdapter(ratingRepository);
    }

    public double calculateAverageRating(ArrayList<Rating> ratings) {
        int totalScore = 0;
        int totalNumber = 0;

        for (Rating rating : ratings) {
            totalScore += rating.getScore();
            totalNumber += 1;
        }

        if (totalNumber > 0)
            return (double) totalScore / (double) totalNumber;
        else
            return 0.0;
    }

    public double calculateWeightedAverage(HashMap<Integer, ArrayList<Rating>> groupedRatingMap) {
        int totalWeights = 0;
        double totalScores = 0.0;
        double weightedAverageScore = 0.0;

        for (Map.Entry<Integer, ArrayList<Rating>> keyValuePair : groupedRatingMap.entrySet()) {
            ArrayList<Rating> ratings = keyValuePair.getValue();

            if (!ratings.isEmpty()) {
                double groupAvg = calculateAverageRating(ratings);
                int similarityScore = keyValuePair.getKey();
                totalWeights += similarityScore;
                totalScores += groupAvg;
            }
        }

        if (totalWeights > 0) {
            weightedAverageScore = totalScores / totalWeights;
        }

        return weightedAverageScore;
    }

    private HashMap<Integer, ArrayList<Rating>> groupedRatingsBySimilarity(
            List<Rating> ratingList, HashMap<Long, User> userMap, String gender, int ageGroup, int occupationNum) {

        UserSimilarityScorer userSimilarityScorer = new UserSimilarityScorer(gender, ageGroup, occupationNum);
        HashMap<Integer, ArrayList<Rating>> groupedRatingMap = new HashMap<>();

        for (Rating rating : ratingList) {
            User user = userMap.get(rating.getUserId());
            int similarityScore = userSimilarityScorer.getScore(user);

            groupedRatingMap.putIfAbsent(similarityScore, new ArrayList<>());
            groupedRatingMap.get(similarityScore).add(rating);
        }

        return groupedRatingMap;
    }

    public HashMap<Long, ArrayList<Rating>> loadGroupedRatingsByMovieId() {
        HashMap<Long, ArrayList<Rating>> groupedRatingMap = new HashMap<>();

        List<Rating> ratingList = ratingRepositoryAdapter.findAll();
        if (ratingList.isEmpty())
            ratingList = ratingRepository.findAll();

        for (Rating rating : ratingList) {
            Long movieId = rating.getMovieId();
            groupedRatingMap.putIfAbsent(movieId, new ArrayList<>());
            groupedRatingMap.get(movieId).add(rating);
        }

        return groupedRatingMap;
    }

    private List<Movie> loadRequestedGenreMovies(List<String> genreNames){
        List<Movie> movieList;

        if (genreNames.size() > 0) {
            movieList = movieRepository.findByGenresIn(genreNames);
        } else {
            movieList = movieRepository.findAll();
        }

        return movieList;
    }

    private HashMap<Long, User> loadAllUsers() {
        HashMap<Long, User> userMap = new HashMap<>();
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            userMap.put(user.getUserId(), user);
        }
        return userMap;
    }

    public HashMap<Long, Double> getMovieScoreMap(UserProfileInputDto input){
        /*
        Weighted rating map is returned.
         */
        HashMap<Long, Double> weightedRatings = new HashMap<>();
        HashMap<Long, ArrayList<Rating>> ratingsGroupedByMovieId = loadGroupedRatingsByMovieId();
        List<Movie> movieList = loadRequestedGenreMovies(input.getGenres());
        HashMap<Long, User> userMap  = loadAllUsers();

        // Calculate movie by movie weighted average score.
        for (Movie movie : movieList) {
            List<Rating> ratingList = ratingsGroupedByMovieId.get(movie.getMovieId());

            if (ratingList == null || ratingList.isEmpty()) {
                continue;
            }

            HashMap<Integer, ArrayList<Rating>> groupedRatingsBySimilarity
                    = groupedRatingsBySimilarity(ratingList, userMap, input.getGender(),
                    input.getAgeGroup(), input.getOccupationNum());

            double weightedAverageScore = calculateWeightedAverage(groupedRatingsBySimilarity);
            weightedRatings.put(movie.getMovieId(), weightedAverageScore);
        }

        return weightedRatings;
    }
}
