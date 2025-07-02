package managers;

import entities.Book;
import exceptions.BookNotFoundException;
import repository.BookRepository;
import utils.BookFileHandler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BookManager {
    private List<Book> books;
    private boolean currentUpdateTracker = false;
    private ExecutorService executorService;

    private BookRepository bookRepository;

//    { //basically does this first after constructing
//        executorService = Executors.newSingleThreadExecutor();
//        books = BookFileHandler.loadBooks();
//        executorService.submit(autoSaveRunnable()); //thread that keeps on goinf
//    }

    public BookManager(BookRepository bookRepository){
        this.bookRepository = bookRepository;

        this.books = this.bookRepository.findAll();

        this.executorService = Executors.newSingleThreadExecutor();
        this.executorService.submit(autoSaveRunnable());

    }

    public BookManager(){
    }

    public void addBook(
            String title,
            String author,
            LocalDate publishDate,
            String genre,
            byte rating,
            float price
    ){
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishDate(publishDate);
        book.setGenre(genre);
        book.setRating(rating);
        book.setPrice(price);
        book.displayBook();

        books.add(book);
        currentUpdateTracker = true;


    }
    public void addBook(
            String title,
            String author,
            LocalDate publishDate,
            String genre
    ){

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishDate(publishDate);
        book.setGenre(genre);
        book.setRating((byte)0);
        book.setPrice(0.0f);
        book.displayBook();
        books.add(book);
        currentUpdateTracker = true;

    }
    public void addBook(String title,
                        String author,
                        LocalDate publishDate,
                        String genre,
                        byte rating
    ){
        currentUpdateTracker = true;

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishDate(publishDate);
        book.setGenre(genre);
        book.setRating(rating);
        book.setPrice(0.0f);
        book.displayBook();
        books.add(book);
        currentUpdateTracker = true;

    }



    public List<Book> getAllBooks(){
     return books;
    }

    public Book getBookById(int bookId){
        return books.stream()
                .filter(book->book.getId()==bookId)
                .findFirst()
                .orElseThrow(()->new BookNotFoundException("Book not found"));
    }

    public Book getBookByTitle(String title){
        for(int i=0;i<books.size(); i++){
            if(books.get(i).getTitle().equalsIgnoreCase(title)) {
                return books.get(i);
            }
        }
        return null;
    }
    public List<Book> getBooksByAuthor(String author){
        List<Book> booksByAuthorBooks = new ArrayList<Book>();
        for(Book book : books){ //enhanced for loop
            if(book.getAuthor().equalsIgnoreCase(author) || book.getAuthor().contains(author) ){
                booksByAuthorBooks.add(book);
            }
        }
        return booksByAuthorBooks;
    }
    public List<Book> getBooksByGenre(String genre){
        return books.stream() //stream way to do loops.  // BASICALLY CONVERTS THE COLLECTION INTO STREAM OBJECT
                .filter(book -> book.getGenre().equalsIgnoreCase(genre) )//filters using lambda expression // Intermediary operations
                .toList();//terminal operator ->ends the stream
    } //rules: on lambda expressions make sure to add parenthesis on more than 2 objects

//    public String bookGenreDistinctString(){
//        String GenreString ="";
//        getAllBooks();
//
//        for( Book book:books){
//            book.displayBook();
//        }
//        return GenreString;
//    }

    public Map<String, Long> getBookGenreStatistics(){
        return books.stream()
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.counting()));
    }
    public List<Book> getSortedBooksByPublishDate(){
        return books.stream()
                .sorted((book1, book2) -> book1.getPublishDate().compareTo(book2.getPublishDate()))
                .toList();
    }
    public boolean updateBook(Book book){
        Book oldBook =books.stream()  //the actual book is inside the book
                .filter(b1->b1.getId() == book.getId()) //filters through the book lists for possible match with the given books' id
                .findAny()//gives the book
                .orElseThrow(()->new BookNotFoundException("Book was not found"));
        if(oldBook.equals(book)){
            return false;
        }
        books.replaceAll(b->b.getId() ==book.getId()? book:b);
        currentUpdateTracker = true;

        return true;

    }

    public boolean updateRating(int bookId, byte rating){
        Book book = books.stream().
                filter(b->b.getId() == bookId).
                findFirst()
                .orElseThrow(()-> new BookNotFoundException("Book was not found"));

        book.setRating(rating);

        //create a separate index array to use as an index locator for
        currentUpdateTracker = true;

        return true;
    }


    public boolean deleteBookById(int bookId){
        try{
            bookRepository.deleteBookByID(bookId);
            return true;
        } catch (SQLException e) {
            System.err.println("Error occured while deleting");
            return false;
        }
    }

    public Runnable autoSaveRunnable(){
        Runnable runnable = () -> {
            while(true){
                if(currentUpdateTracker){
//                    BookFileHandler.saveBooks(books);
                    books.forEach(book ->{
                        if(book.getId() <= 0){
                            bookRepository.save(book);
                            System.out.println("saved");
                        } else if(!book.getTitle().isBlank()){
                            try {
                                bookRepository.updateBook(book);
                            } catch (SQLException e) {
                                System.err.println("error occured when update book with id:" +
                                        book.getId() + " Error Messege" + e.getMessage());
                            }
                        }
                    });
                    currentUpdateTracker =false;
                }
                try {
                    Thread.sleep(5000);

                }catch (InterruptedException e){
                    System.err.println("Error in threading : " + e.getMessage());
                }
            }
        };
        return runnable;
    }
    public void shutDownAutoSave(){
        executorService.shutdown();
        System.out.println("Auto Save Thread Shutting Down! ");
    }
}
