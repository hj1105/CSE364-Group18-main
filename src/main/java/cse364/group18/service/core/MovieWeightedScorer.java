package cse364.group18.service.core;


public class MovieWeightedScorer implements Comparable<MovieWeightedScorer> {

    private int count;
    private double sum;

    public MovieWeightedScorer() {
        sum = 0;
        count = 0;
    }

    public MovieWeightedScorer(double sum, int count) {
        this.count = count;
        this.sum = sum;
    }

    public void updateRecord(double weightedScore) {
        this.sum += weightedScore;
        this.count += 1;
    }

    public double getAverage() {
        if (count > 1)
            return sum / count;
        else
            return 0.0;
    }

    @Override
    public int compareTo(MovieWeightedScorer score) {
        return Double.compare(score.getAverage(), this.getAverage());
    }
}
