package ui;

import entities.Book;
import managers.BookManager;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class BookHubConsole {
    private BookManager bookManager;
    private Scanner scanner;

    public BookHubConsole(){
        bookManager = new BookManager();
        scanner = new Scanner(System.in);
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
                case 8:
                    updateBook();
                    break;
                case 10:
                    deleteById();
                    break;
                case 11:
                    System.out.println("Goodbye!");
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
            System.out.printf("Id: %d, Title: %s", book.getId(), book.getTitle());
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
        System.out.println("Choose a Title");
        String choice = scanner.nextLine();
        scanner.nextLine();
        System.out.println(bookManager.getBookByTitle(choice).toString());

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
