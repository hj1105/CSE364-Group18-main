package cse364.group18.repository;

import cse364.group18.exception.NotFoundException;
import cse364.group18.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Repository
public class RatingRepositoryAdapter {
    // To reduce DB access time, rating data are cached.
    RatingRepository ratingRepository;
    List<Rating> ratingList;
    HashMap<Long, List<Rating>> ratingMap = new HashMap<>();

    public RatingRepositoryAdapter(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
        this.ratingList = ratingRepository.findAll();
    }

    public List<Rating> findAll() {
        if (this.ratingList.isEmpty())
            this.ratingList = ratingRepository.findAll();

        for (Rating rating : this.ratingList) {
            Long userId = rating.getUserId();

            if (ratingMap.containsKey(userId)) {
                List<Rating> rList = ratingMap.get(userId);
            } else {
                List<Rating> rList = new ArrayList<>();
                rList.add(rating);
                ratingMap.put(userId, rList);
            }
        }

        return this.ratingList;
    }

    public List<Rating> findByUserId(Long userId) throws NotFoundException {
        if (this.ratingMap.isEmpty())
            findAll();

        if (this.ratingMap.containsKey(userId)) {
            return this.ratingMap.get(userId);
        } else {
            throw new NotFoundException("Not found user id: ", userId.toString());
        }
    }
}
