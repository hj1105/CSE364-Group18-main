package cse364.group18.repository;

import cse364.group18.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RatingRepository extends MongoRepository<Rating, Long> {
    List<Rating> findAll();
    List<Rating> findByMovieId(Long movieId);
}