package managers;

import entities.Book;
import entities.Review;
import exceptions.ReviewInputException;

import java.util.List;

public class ReviewManager {
    public static List<Review> getAllBookReviews(Book book){
        return book.getReviews();
    }

    public static Review getBookReviewById(Book book, int reviewId){
        for(Review review: book.getReviews()){
            if(review.getReviewId() == reviewId) {
                return review;
            }
        }
        return null;
    }
    public static void addRe15view(Book book,
                                 String username,
                                 String comment,
                                 byte rating){
        if(comment.isBlank()) throw new ReviewInputException("Comment can not be empty for a review");
        if(rating < 0 || rating > 5) throw new ReviewInputException("Rating cannot exceed 5 or go below 0");
        Review review = new Review(username,comment,rating);
        book.getReviews().add(review);
        System.out.println("Review was successfully added!");
    }

    public static boolean updateReview(Book book, int reviewId, String comment, byte rating){
        boolean results = false;
        if(updateComment(book, reviewId,comment)||updateRating(book, reviewId,rating)){
            results = true;
        }

        return results;

    }
    private static boolean updateComment(Book book, int reviewId, String comment){
        Review review = getBookReviewById(book, reviewId);
        if( !review.getComment().equals(comment) || !comment.isBlank()){
            review.setComment(comment);
            return true;
        }
        return false;


    }

    private static boolean updateRating(Book book, int reviewId, byte rating){
        Review review = getBookReviewById(book, reviewId);
        if(review.getRating() != rating && !(rating < 0 || rating > 5)) {
            review.setRating(rating);
            return true;
        }
            return false;
    }



    public static boolean deleteReviewById(Book book, int reviewId){
       return book.getReviews().removeIf(b->b.getReviewId() == reviewId);

    }
}
