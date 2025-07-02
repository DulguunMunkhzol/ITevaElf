package ui;

import entities.Book;
import exceptions.BookNotFoundException;
import managers.BookManager;
import repository.DataBaseConnection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class BookHubConsole {
    private BookManager bookManager;
    private Scanner scanner;

    public BookHubConsole(BookManager bookManager, Scanner scanner){
        this.bookManager =  bookManager;
        this.scanner =  scanner;
    }

    public void start(){
        System.out.println("Welcome to BookHub!");

        while(true){
            displayMenu();
            int choice = scanner.nextInt();

            switch(choice){
                case 1:
                    addBook();
                    break;
                case 2:
                    getAllBooks();
                    break;
                case 3:
                    getBookByTitle();
                    break;
                case 4:
                    getBooksByAuthor();
                    break;
                case 5:
                    getBooksByGenre();
                    break;
                case 6:
                    getBooksByGenreStat();
                    break;
                case 7:
                    getSortedBooks();
                    break;
                case 8:
                    updateBook();
                    break;
                case 9:
                    updateBookRating();
                    break;
                case 10:
                    deleteById();
                    break;
                case 11:
                    System.out.println("Goodbye!");
                    bookManager.shutDownAutoSave();
                    System.out.println("AutoSave Shutdown!");

                    scanner.close();
                    System.out.println("Scanner close!");

                    try{
                        System.out.println("Closing connection!");
                    DataBaseConnection.getInstance().getConnection().close();
                        System.out.println("Connection close!");

                    }catch (Exception e){
                        System.err.println("Error occured while closing the connection");
                    }
                    System.exit(0);

                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void displayMenu(){
        System.out.println("""
                +-------------------------------+
                |           MAIN MENU           |
                +-------------------------------+
                | 1.Add a Book                  |
                | 2.Get All Books               |
                | 3.Get Books by Title          |
                | 4.Get Books by Author         |
                | 5.Get Books by Genre          |
                | 6.Get Books Genre Stats       |
                | 7.Get Sorted BookList         |
                | 8.Update a Book               |
                | 9.Update Rating of Book       |
                | 10.Delete a Book              |
                | 11.Exit                       |
                +-------------------------------+
                """);
    }

    private void displayBookIds(){
        System.out.println("current Book Ids");
        bookManager.getAllBooks().forEach(book ->{
            System.out.printf("Id: %d, Title: %s \n", book.getId(), book.getTitle());
        });
    }

    private void addBook(){
        scanner.nextLine();
        System.out.println("Enter Title: ");
        String titleString = scanner.nextLine();

        System.out.println("Enter Author: ");
        String authorString = scanner.nextLine();

        System.out.println("Enter Published Date in (YYYY-MM-DD): ");
        LocalDate publishDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter Genre: ");
        String genString = scanner.nextLine();

        System.out.println("Enter Rating (optional)");
        String ratingOptional = scanner.nextLine();

        System.out.println("Enter Price(optional): ");
        String priceOptional =scanner.nextLine();


        if(!ratingOptional.isBlank()){
            if(!priceOptional.isBlank()){
                bookManager.addBook(titleString,
                        authorString,
                        publishDate,
                        genString,
                        Byte.parseByte(ratingOptional),
                        Float.parseFloat(priceOptional));
                return;
            }
            bookManager.addBook(titleString,
                    authorString,
                    publishDate,
                    genString,
                    Byte.parseByte(ratingOptional));
            return;
        }
        bookManager.addBook(titleString,
                authorString,
                publishDate,
                genString);
    }

    private void getAllBooks(){
        if(bookManager.getAllBooks().isEmpty()){
            System.out.println("The Library is empty you Donut!");
            return;
        }
        System.out.println("Your Books");
        bookManager.getAllBooks().forEach(Book::displayBook);
    }

    private void getBookByTitle(){
        if(bookManager.getAllBooks().isEmpty()){
            System.out.println("the Library is empty!");
            return;
        }
        bookManager.getAllBooks().forEach(book->System.out.printf("\n Titles: %s", book.getTitle()));
        System.out.println("\nChoose a Title");
        String choice = scanner.nextLine();
        scanner.nextLine();
        System.out.println(bookManager.getBookByTitle(choice).toString());

    }
    private void getBooksByAuthor(){
        List<Book> booksByAuthor = new ArrayList<Book>();
        if(bookManager.getAllBooks().isEmpty()){
            System.out.println("The library is EMPTY!!!!");
            return;
        }
//        bookManager.getAllBooks().forEach(book -> System.out.printf("\nAurhors: %s", book.getAuthor()));
        printBooksDistinctlyAuthor();
        System.out.println("\nPlease Choose an Author from the List:  ");

        scanner.nextLine();
        String choice = scanner.nextLine();

        booksByAuthor = bookManager.getBooksByAuthor(choice);

        if(booksByAuthor.isEmpty()){
            if(choice.isBlank()){
                System.out.println("Choice empty");
                return;
            }
            System.out.println("There are no BOOKS with this AUTHOR");
            return;
        }
        booksByAuthor.forEach(Book::displayBook);
    }
    public void printBooksDistinctlyAuthor(){
        List <String> bookAuthorString = bookManager
                .getAllBooks()
                .stream()
                .map(Book::getAuthor)
                .distinct()
                .toList();

        bookAuthorString.forEach(s->System.out.println("Author option: " + s));
    }


    public void getBooksByGenre(){
        List<Book> booksByGenre = new ArrayList<Book>();
        if(bookManager.getAllBooks().isEmpty()){
            System.out.println("The Library is Empty, please fill the Books");
            return;
        }

//        bookManager.getAllBooks().forEach(book ->System.out.printf("Current Genres: %s \n",book.getGenre()));
        printBookGenresDistictly();
        System.out.println("\nPlease Choose a Genre from the List:  ");
        scanner.nextLine();
        String choice = scanner.nextLine();
        if(choice.isBlank()){
            System.out.println("The field is empty!!\n   to Exit the 5th menu Item, \nEnter \"Please Exit OMG\" \n");
            choice = scanner.nextLine();
        }
        booksByGenre = bookManager.getBooksByGenre(choice);

        if(booksByGenre.isEmpty()){
            if(choice.isBlank()){
                System.out.println("Enter something next time!!");
                return;
            }
            System.out.println("There are no BOOKS with this GENRE");
            return;
        }
        booksByGenre.forEach(Book::displayBook);

    }
    private void printBookGenresDistictly(){
        List<String> bookGenresString =  bookManager.getAllBooks().stream().map(Book::getGenre).distinct().toList();
        bookGenresString.forEach(b->System.out.printf("\nGenre option: " + b));
    }


    private void getBooksByGenreStat(){
        bookManager.getBookGenreStatistics().forEach((genre,stats)->System.out.println("Genre: " + genre + " || Statistics: " + stats));
    }

    private void getSortedBooks(){
        bookManager.getSortedBooksByPublishDate().forEach(b->b.displayBook());
    }

    private void updateBookRating(){
        int bookIdToChange = 1;
        final int fInt;
        byte newRating =0;
        System.out.println("Please Enter Book Id to Change");
        displayBookIds();
        bookIdToChange = scanner.nextInt();
        fInt = bookIdToChange;
        scanner.nextLine();
        Book foundBook = bookManager
                .getAllBooks()
                .stream()
                .filter(b->b.getId()==fInt)
                .findFirst()
                .get();
        ;

        System.out.println("Changing Book Titled: " +
                foundBook.getTitle() +
                "\nPlease Enter New Rating\n" +
                "Current Rating: \n" +
                foundBook.getRating() +
                "\nChange to: ");

        newRating = scanner.nextByte();
        scanner.nextLine();

        bookManager.updateRating(bookIdToChange,newRating);
    }
//    bookManager.getAllBooks().forEach(book ->{
//        System.out.printf("Id: %d, Title: %s", book.getId(), book.getTitle());
//    });

    private void updateBook(){
        displayBookIds();
        System.out.println("Please Choose an Id: ");
        int choice = scanner.nextInt();
        scanner.nextLine();


        try{
            Book existingBook = bookManager.getBookById(choice);
            System.out.println("Current book information: ");
            existingBook.displayBook();

            System.out.println("Enter New title (current: ".concat(existingBook.getTitle()).concat(" ): "));
            String newTitleString = scanner.nextLine();
            if(newTitleString.isBlank()) newTitleString = existingBook.getTitle();

            System.out.println("Enter new Author (current: ".concat(existingBook.getAuthor()).concat(" ): "));
            String newAuthorString = scanner.nextLine();
            if(newAuthorString.isBlank()) newAuthorString = existingBook.getAuthor();

            System.out.println("Enter new published date (current: "
                    .concat(existingBook
                            .getPublishDate()
                            .toString())
                    .concat(" ) [YYYY-MM-DD] : "));
            String dateInputString = scanner.nextLine();
            LocalDate newDate = dateInputString.isBlank() ? existingBook.getPublishDate() : LocalDate.parse(dateInputString);

            System.out.println("Enter new Genre (current: ".concat(existingBook.getGenre()).concat(" ): "));
            String newGenreString = scanner.nextLine();
            if (newGenreString.isBlank()) newGenreString = existingBook.getGenre();

            System.out.println("Enter new Price (current: "+ existingBook.getPrice() + " ): ");
            String newPriceString = scanner.nextLine();
            float newPrice = newPriceString.isBlank() ? existingBook.getPrice() : Float.parseFloat(newPriceString);

            Book updatedBook = new Book(existingBook.getId() , existingBook.getDateAdded());
            updatedBook.setTitle(newTitleString);
            updatedBook.setAuthor(newAuthorString);
            updatedBook.setGenre(newGenreString);
            updatedBook.setPrice(newPrice);
            updatedBook.setPublishDate(newDate);
            updatedBook.setRating(existingBook.getRating());

            if (bookManager.updateBook(updatedBook)){
                System.out.println("Book updated successfully!");
            }else{
                System.out.println("no changes made");
            }
        }catch (Exception e){
            System.err.println("Book not found or invalid input");
        }

    }
    private void deleteById(){
        displayBookIds();
        System.out.println("Please Choose an Id: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
//change and correct this

//        if(bookManager.getBookById(choice) == ){
//            bookManager.deleteBookById(choice);
//        }else{
//
//        }
        try{
            bookManager.deleteBookById(choice);
        }catch (Exception e){
            throw new RuntimeException("book not found");
        }
    }

}
