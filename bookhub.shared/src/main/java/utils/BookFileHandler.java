package utils;

import entities.Book;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookFileHandler {
    private static final String BOOK_FILE = "bookhub_data.csv";

    public static void saveBooks(List<Book> books){
        try(FileWriter fileWriter = new FileWriter(BOOK_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            for (Book book:books) {
                bufferedWriter.write(bookToString(book));
                bufferedWriter.newLine();
            }
            System.out.println("Books Saved successfully");

        }catch (IOException e){
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    private static String bookToString (Book book ){
        //format: id,title,author,genre,publishdate,price,rating,dateAdded
        LocalDateTime shortenedDateTime = book.getDateAdded().truncatedTo(ChronoUnit.SECONDS); //trims (trunicates) the nanoseconds from the LocalDateTime
        return String.format("%d,%s,%s,%s,%s,%.02f,%d,%s",
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublishDate(),
                book.getPrice(),
                book.getRating(),
                book.getDateAdded()
                );
    }

    public static List<Book> loadBooks(){
        List<Book> books = new ArrayList<Book>();
        File file = new File(BOOK_FILE);
        if(!file.exists()){
            return books;
        }

        try(FileReader freader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(freader)){
            String lineString;
            while((lineString = bufferedReader.readLine()) !=null){
                Book book = stringToBook(lineString);
                if(book != null || book.getTitle() !=null){
                    books.add(book);
                }
            }
        }catch(IOException e){
            System.err.println("Error Loading books: "+ e.getMessage());

        }
        return books;
    }

    private static Book stringToBook(String bookString){
        Book book = new Book();
        String[] bookStrings = bookString.split(",");
        book.setId(Integer.parseInt(bookStrings[0]));
        book.setTitle(bookStrings[1]);
        book.setAuthor(bookStrings[2]);
        book.setGenre(bookStrings[3]);
        book.setPublishDate(LocalDate.parse(bookStrings[4]));
        book.setPrice(Float.parseFloat(bookStrings[5]));
        book.setRating(Byte.parseByte(bookStrings[6]));
        book.setDateAdded(LocalDateTime.parse(bookStrings[7]));

//        int pointer = 0;
//        for(Field field: book.getClass().getDeclaredFields()){
//            if(pointer > 8) break;
//            try{
//                field.setAccessible(true);
//                field.set(book, bookStrings[pointer++]);
//                field.setAccessible(false);
//            }catch(IllegalArgumentException | IllegalAccessException e){
//                System.out.println("error has occured during parsingL " + e.getMessage());
//            }
//        }
        return book;
    }

}
