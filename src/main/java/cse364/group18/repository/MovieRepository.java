package cse364.group18.repository;

import cse364.group18.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MovieRepository extends MongoRepository<Movie, Long> {
    Movie findByMovieId(Long movieId);
    List<Movie> findByTitleIgnoreCase(String title);
    Optional<Movie> findByTitleAndYear(String title, String year);
    List<Movie> findByGenresIn(List<String> genres);
}