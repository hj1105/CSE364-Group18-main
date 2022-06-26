package cse364.group18.service.util;
import cse364.group18.model.User;
import cse364.group18.service.core.UserSimilarityScorer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class UserSimilarityScorerTest {
    UserSimilarityScorer userSimilarityScorer;

    public UserSimilarityScorerTest() {
        String gender = "F";
        int age = 56;
        int occupationNum = 1;
        userSimilarityScorer = new UserSimilarityScorer(gender, age, occupationNum);
    }

    @Test
    public void zeroMatchTest() {
        User user = new User(12L, "M",  16, 20, "12134");
        int similarityScore = userSimilarityScorer.getScore(user);
        Assertions.assertEquals(similarityScore, 0);
    }

    @Test
    public void oneMatchTest() {
        User user = new User(12L, "F",  16, 20, "12134");
        int similarityScore = userSimilarityScorer.getScore(user);
        Assertions.assertEquals(similarityScore, 1);
    }

    @Test
    public void twoMatchTest() {
        User user = new User(12L, "F",  56, 20, "12134");
        int similarityScore = userSimilarityScorer.getScore(user);
        Assertions.assertEquals(similarityScore, 2);
    }

    @Test
    public void threeMatchTest() {
        User user = new User(12L, "F",  56, 1, "12134");
        int similarityScore = userSimilarityScorer.getScore(user);
        Assertions.assertEquals(similarityScore, 3);
    }

}
