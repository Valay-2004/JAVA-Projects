package services;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LibraryManager {
    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Author> authors = new HashMap<>();
    private final Map<String, Loan> loans = new HashMap<>();
    private final Map<String, Patron> patrons = new HashMap<>();


    // methods for books
    public void addBook(Book book){
        books.put(book.getId(), book);
    }

    public Book findBookById(String id){
        if(id == null) return null;

        // get the book id (if found)
        return books.get(id);
    }

    // methods for patron
    public void addPatron(Patron patron){
        patrons.put(patron.getId(), patron);
    }
    public Patron findPatronById(String id){
        if(id == null) return null;
        return patrons.get(id);
    }
}
