package ua.ievleva.movieland.entity;


public class Review {
    private String reviewText;

    public Review(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
