package repository;

import entities.Review;

import java.sql.Connection;

public class ReviewRepository {
    private Connection connection;
    public ReviewRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }
    public Review save(Review review){

        return review;
    }
}
