package cse364.group18.service.core;

import cse364.group18.model.User;


public class UserSimilarityScorer {

    private final String gender;
    private final int age;
    private final int occupationNum;

    public UserSimilarityScorer(String gender, int age, int occupationNum) {
        this.gender = gender;
        this.age = age;
        this.occupationNum = occupationNum;
    }

    public int getScore(User user) {
        int score = 0;

        if ((user.getGender()).equals(this.gender)) {
            score += 1;
        }

        if (user.getAgeGroup() == this.age) {
            score += 1;
        }

        if (user.getOccupation() == this.occupationNum) {
            score += 1;
        }

        return score;
    }
}
