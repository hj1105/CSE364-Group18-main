package cse364.group18.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
@NoArgsConstructor
public class Rating {

    private Long userId;
    private Long movieId;
    private int score;
    private int timeStamp;

    public Rating(Long userId, Long movieId, int score, int timeStamp) {
        this.userId = userId;
        this.movieId = movieId;
        this.score = score;
        this.timeStamp = timeStamp;
    }

}