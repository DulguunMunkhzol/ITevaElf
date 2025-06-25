package org.example;

import entities.Book;
import managers.BookManager;
import ui.BookHubConsole;
import utils.BookFileHandler;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
//
//        Scanner scanner = new Scanner(System.in);
//
//        BookManager bookManager = new BookManager();
//
//        bookManager.addBook("Clean Code","Martin Luther King", LocalDate.of(2002,2,2),"Non-Fiction");
//        bookManager.addBook("Clean Code","Martin Luther King", LocalDate.of(2002,2,2),"Non-Fiction");
//        bookManager.addBook("Clean Code","Martin Luther King", LocalDate.of(2002,2,2),"Non-Fiction");

//        Book book1 = new Book();
//        Book book2 = new Book();
//        Book book3 = new Book();
//        Book book4 = new Book();
//        Book book5 = new Book();
//        Book book6 = new Book();
//
//        Book book7 = new Book(
//                "clean Code",
//                "test",
//                LocalDate.of(2002,2,3),
//                10.99f,
//                "non-fiction",
//                (byte)5);
//
//        book1.setTitle("Clean Code");
//        book1.setAuthor("Robert C. Martin");
//        book1.setPublishDate(LocalDate.of(2008,8,1));
//        book1.setGenre("Non-Fiction");
//        book1.setPrice(59.99f);
//        book1.setRating((byte)4);

//        book2.setTitle("The Clean Coder:" +
//                "\nA Code of Conduct for Professional Programmers");
//        book2.setAuthor("Robert C. Martin");
//        book2.setPublishDate(LocalDate.of(2011, 5, 4));
//        book2.setGenre("Non-Fiction");
//        book2.setPrice(49.99f);
//        book2.setRating((byte)4);
//
//        book3.setTitle("Clean Architecture:" +
//                "\nA Craftsman's Guide to Software \nStructure and Design");
//        book3.setAuthor("Robert C. Martin");
//        book3.setPublishDate(LocalDate.of(2012, 3, 15));
//        book3.setGenre("Non-Fiction");
//        book3.setPrice(49.99f);
//        book3.setRating((byte)4);
//
//        book4.setTitle("Agile Estimating and Planning");
//        book4.setAuthor("Robert C. Martin");
//        book4.setPublishDate(LocalDate.of(2005,11,1));
//        book4.setGenre("Non-Fiction");
//        book4.setPrice(56.99f);
//        book4.setRating((byte)3);
//
//        book5.setTitle("the Software Craftsman: " +
//                "\n Professionalism, Pragmatism, Pride");
//        book5.setAuthor("Robert C. Martin");
//        book5.setPublishDate(LocalDate.of(2014,12,24));
//        book5.setGenre("Science");
//        book5.setPrice(39.99f);
//        book5.setRating((byte)5);
//
//        book6.setTitle("Clean Agile: " +
//                "\nBack to Basics");
//        book6.setAuthor("Robert C. Martin");
//        book6.setPublishDate(LocalDate.of(2019,10,17));
//        book6.setGenre("Non-Fiction");
//        book6.setPrice(34.77f);
//        book6.setRating((byte)3);
//
//
//        book1.displayBook();
//        book2.displayBook();
//        book3.displayBook();
//        book4.displayBook();
//        book5.displayBook();
//        book6.displayBook();
//        book7.displayBook();

//        System.out.println("Please enter the first number!");
//        int num = scanner.nextInt();
//        System.out.println("Please enter the seconds Number!");
//        int num2 = scanner.nextInt();
//        try{
//            int results = num/num2;
//        }catch(ArithmeticException e){
//            System.out.println("you can't devide by Zero");
//        } finally {
//
//        }
//        accessFile();
//
//        bookManager.updateRating(1,(byte)5);
//        bookManager.deleteBookById(2);
//        bookManager.updateRating(1,(byte)4);
//        bookManager.updateRating(2,(byte)4);
//        bookManager.updateRating(3,(byte)4);
//
//        System.out.println(bookManager.getBookGenreStatistics());

        BookHubConsole console =new BookHubConsole();
        console.start();


//        List<Book> books = new ArrayList<Book>();
//        books.add(new Book(
//                "To Kill a Mockingbird",
//                "Harper Lee",
//                LocalDate.of(1960, 7, 11),
//                12.99f,
//                "Fiction",
//                (byte) 5)
//        );
//        books.add(new Book(
//                "To Kill a Mockingbird",
//                "Harper Lee",
//                LocalDate.of(1960, 7, 11),
//                12.99f,
//                "Fiction",
//                (byte) 5)
//        );
//        BookFileHandler.saveBooks(books);
//        List<Book> newList = BookFileHandler.loadBooks();
//        books.forEach(System.out :: println);
//        newList.forEach(System.out::println);
    }

//    private static void accessFile() throws IOException{
//        FileReader fReader = new FileReader("bad_file.txt");
//    }

}