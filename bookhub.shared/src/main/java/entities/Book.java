package entities;

import exceptions.BookInputException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Book implements Comparable <Book>, Comparator<Book> {
    //initializes the stuff then it sets it to 0
    //container contains no value, only data types
    //encapsulation
    //control the flow of data coming in and out of classes
    //private field variables
    // public getters and setters
    //number primitive types
    //byte (8 bits) (-128 -> 127), short (16 bits) (-32,768 -> 32,767), int (32 bits), long (64 bits)  => non-decimal
    //double, float => decimal
    private int id;
    private String title;
    private String author;
    private LocalDate publishDate;
    private float price;
    private String genre;
    private byte rating;
    private LocalDateTime dateAdded;
    private List<Review> reviews;


    //constructor
    //Is a special method that initializes the instance object from the class object
    // it called in conjunction with the new keyword
    //if not done, java will build a default constructor
    //example:
//    public Book(){}
//
    public Book(
    String title,
    String author,
    LocalDate publishDate,
    float price,
    String genre,
    byte rating){
        if(title == null || title.trim().isEmpty()){
            throw new BookInputException("Title needs to be filled out!");
        }
        if(author == null||author.trim().isEmpty()){
            throw new BookInputException("Author needs to be filled out!");
        }
        if(publishDate == null){
            throw new BookInputException("Have to have a publish date whether future or past");
        }
        if(genre ==null||genre.trim().isEmpty()){
            throw new BookInputException("Genre is required to be filled out!");
        }

        this.title = title;
        this.author= author;
        this.publishDate = publishDate;
        this.price = price;
        this.genre = genre;
        this.rating = rating;
        this.reviews = new ArrayList<Review>();
        dateAdded = LocalDateTime.now();

    }

    public Book(int id, LocalDateTime dateAdded){
        this.id = id;
        this.dateAdded = dateAdded;
        this.reviews = new ArrayList<Review>();
    }


    public Book(){

        this.dateAdded =LocalDateTime.now();
        this.reviews = new ArrayList<Review>();


    };



    //getters and setters are used to access the field variables in the instance object


    /**this is what we call java doc
     * this is a method to retrieve the id of the book
     * @return int
     * */
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public void setDateAdded(LocalDateTime dateAdded){
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null || title.trim().isEmpty()){
        throw new BookInputException("Title needs to be filled out!");
        }

            this.title = title;

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if(author == null||author.trim().isEmpty()){
            throw new BookInputException("Author needs to be filled out!");
        }
            this.author = author;

    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        if(publishDate == null){
            throw new BookInputException("Have to have a publish date whether future or past");
        }
            this.publishDate = publishDate;

    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if(publishDate == null) {
            throw new BookInputException("Have to have a publish date whether in future or past!");
        }
        this.price = price;
        if(price < 0) {
            throw new BookInputException("Price cannot be negative!");
        }
        this.price = price;
    }

    public String getGenre() {
            return genre;
    }

    public void setGenre(String genre) {
        if(genre ==null||genre.trim().isEmpty()){
            throw new BookInputException("Genre is required to be filled out!");
        }
        this.genre = genre;

    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void displayBook(){
        System.out.println("Book Information:\n" +
                "id: " + getId() +
                "\nTitle: " + getTitle() +
                "\nAuthor: " + getAuthor() +
                "\nPublish Date: " + getPublishDate()+
                "\nPrice: $" + getPrice() +
                "\nGenre: " + getGenre() +
                "\nCurrent Rating: " + getRating() + " Stars \n"
                );
    }

    public LocalDateTime getDateAdded(){
        return dateAdded;
    }

    public int compareTo(Book o){
        if(this.title.compareTo(o.title) == 1){
            return 1;
        }else if(this.title.compareTo(o.title) == -1){
            return -1;
        }
        return 0;
    }

    @Override
    public int compare(Book o1, Book o2) {
        if (o1.title.equalsIgnoreCase(o2.title) &&
                o1.author.equalsIgnoreCase(o2.author) &&
                o1.publishDate.equals(o2.publishDate)
        ) {
        return 1;
        }else if( !o1.title.equalsIgnoreCase(o2.title) &&
                !o1.author.equalsIgnoreCase(o2.author) &&
                !o1.publishDate.equals(o2.publishDate)){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishDate=" + publishDate +
                ", price=" + price +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return id == book.id && Float.compare(price, book.price) == 0
                && rating == book.rating && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && Objects.equals(publishDate, book.publishDate)
                && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publishDate, price, genre, rating);
    }
}
