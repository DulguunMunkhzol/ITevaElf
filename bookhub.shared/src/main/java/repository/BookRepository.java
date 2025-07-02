package repository;

import entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private Connection connection;

    public BookRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public  Book save(Book book){
        String sqlString = "INSERT INTO books (title, author, publish_date, price, genre, rating) " +
                "VALUE (?, ?, ?, ?, ?, ?)";
        String[] returnedColumnString = {"id","date_added"};
        try (PreparedStatement statement = connection.prepareStatement(sqlString, returnedColumnString)){
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setDate(3, Date.valueOf(book.getPublishDate()));
            statement.setFloat(4, book.getPrice());
            statement.setString(5, book.getGenre());
            statement.setByte(6, book.getRating());
            int rows = statement.executeUpdate();
            if(rows == 0 ) throw new SQLException("Creating book failed, no rows affected");
            try(var generatedKeys = statement.getGeneratedKeys()){
                if(generatedKeys.next()) {
                    int bookId = generatedKeys.getInt(1);
                    book.setId(bookId);
                    String fetchTimestampSql = "SELECT date_added FROM books WHERE id = ?";
                    try (PreparedStatement fetchStmt = connection.prepareStatement(fetchTimestampSql)) {
                        fetchStmt.setInt(1, bookId);
                        try (var rs = fetchStmt.executeQuery()) {
                            if (rs.next()) {
                                book.setDateAdded(rs.getTimestamp("date_added").toLocalDateTime());
                            }
                        }
                    }
                }
            }
            return book;
        }catch (SQLException e){
            System.err.println("Error has occured,while savings: " + e.getMessage());
        }
        return null;
    }

    public List<Book> findAll(){
        List<Book> books = new ArrayList<Book>();
        String sqlString = "SELECT * FROM books";
        try(Statement statement = connection.createStatement();
                ResultSet bookSet = statement.executeQuery(sqlString)){
            while(bookSet.next()){
                Book book = new Book();
                book.setId(bookSet.getInt(1));
                book.setTitle(bookSet.getString(2));
                book.setAuthor(bookSet.getString(3));
                book.setPublishDate(bookSet.getDate(4).toLocalDate());
                book.setPrice(bookSet.getFloat(5));
                book.setGenre(bookSet.getString(6));
                book.setRating(bookSet.getByte(7));
                book.setDateAdded(bookSet.getTimestamp(8).toLocalDateTime());
                books.add(book);
                book = null;
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Book updateBook(Book book) throws SQLException{
        String sqlString = "UPDATE books SET title =?, author = ?, publish_date = ?, price = ? ," +
                " genre = ? , rating = ? WHERE id = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlString)){
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2,book.getAuthor());
            preparedStatement.setDate(3, Date.valueOf(book.getPublishDate()));
            preparedStatement.setFloat(4, book.getPrice());
            preparedStatement.setString(5, book.getGenre());
            preparedStatement.setByte(6, book.getRating());
            preparedStatement.setInt(7,book.getId());
            int row = preparedStatement.executeUpdate();
            if(row > 0 ){
                System.out.println("book update successful");
            }else{
                System.err.println("An error has occured while updating data!");
            }

        }
        return book;
    }

    public void deleteBookByID(int id) throws SQLException{
        String sqlString = "DELETE FROM books WHERE id=? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlString)){
            preparedStatement.setInt(1,id);
            int row = preparedStatement.executeUpdate();
            if(row>0){
                System.out.println("book was deleted");
            }else{
                System.err.println("eror");
            }

        }
    }
}


