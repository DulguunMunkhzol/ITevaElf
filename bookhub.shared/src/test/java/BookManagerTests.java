import entities.Book;
import managers.BookManager;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookManagerTests {
    private BookManager bookManager;

    @BeforeEach
    public void setUp(){
        bookManager = new BookManager();
        bookManager.addBook( "test123","author123", LocalDate.of(1233,11,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,11,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,2,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,2,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,2,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,2,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,2,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,2,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,2,12),"Programming",(byte)4,49.99f);
        bookManager.addBook( "test123","author123", LocalDate.of(1233,2,12),"Programming",(byte)4,49.99f);

    }

    @AfterEach
    public void cleanUp(){
        bookManager = null;

    }
    @Test
    @DisplayName("Should give acurate size of array when tested!")
    void testGetAllBooksSuccessTest(){
        assertEquals(10, bookManager.getAllBooks().size());
    }

    @Test
    @DisplayName("should give accurate size of array")
    void testGetAllBooks(){
        assertEquals(10,bookManager.getAllBooks().size());
    }

    @Test
    @DisplayName("Should update the book in the array")
    void textUpdateBookShouldUpdateTheBook(){
        Book oldBook = bookManager.getBookById(13);

        Book expectedBook = new Book(oldBook.getId(), oldBook.getDateAdded());

        expectedBook.setTitle("Dirty Code");
        expectedBook.setAuthor("Muhammad Ahmadi");
        expectedBook.setGenre("non-fiction");
        expectedBook.setPublishDate(LocalDate.of(2013,2,23));
        expectedBook.setRating((byte)0);


        assertTrue(bookManager.updateBook(expectedBook));
        Book updateBook = bookManager.getBookById(13);

        assertEquals(expectedBook.getTitle(), updateBook.getTitle());
        assertEquals(expectedBook.getAuthor(), updateBook.getAuthor());
    }


}
