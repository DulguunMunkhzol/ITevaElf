package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

public class Review implements Comparable <Review> {
    private int reviewId;
    private String username;
    private String comment;
    private byte rating;
    private LocalDateTime dateCreated;


    public Review(){
        this.dateCreated = LocalDateTime.now();
    }

    public Review(String username, String comment, byte rating){
        this.username = username.isBlank() ? "Anonymous User" : username;
        this.dateCreated = LocalDateTime.now();
        this.comment = comment;
        this.rating = 0;

    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }


    @Override
    public int compareTo(Review o) {
        return this.dateCreated.compareTo(o.getDateCreated());
    }
}
