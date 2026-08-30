package services;
import model.*;

import java.time.LocalDate;
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


    public boolean issueLoan(String loanId, String bookId, String patronId, LocalDate loanDate, LocalDate dueDate) {
        // 1. Check if book and patron exist
        if(!books.containsKey(bookId) || !patrons.containsKey(patronId)){
            System.out.println("Given Book ID or Patron ID does not exist!");
            return false;
        }
        // additional check for loanId if they by any chance collide!
        if(loans.containsKey(loanId)){
            System.out.println("Loan ID already exists!");
            return false;
        }
        // 2. Check if the patron has reached their maxBooksAllowed
        Patron patron = findPatronById(patronId);
        int currCount = 0;
        for(Loan loan : loans.values()){
            if(loan.getPatronId().equals(patronId) && loan.getReturnDate() == null) currCount++;
        }

        if(currCount >= patron.getMaxBooksAllowed()){
            System.out.println("Your Quota for allowed books is full! Please return some books and try again!");
            return false;
        }
        // 3. If all good, create the Loan, add it to the map, and return true
        Loan newLoan = new Loan(loanId, bookId, patronId, loanDate, dueDate);
        loans.put(newLoan.getId(), newLoan);
        // 4. If any check fails, return false
        System.out.println("Your Loan id: " + newLoan.getId() + " with due date: " + newLoan.getDueDate() + " is added successfully!");
        return true;
    }

    // TODO -- returnBook
    public boolean returnBook(String loanId, LocalDate returnDate) {
        // 1. Find the loan using loanId
            Loan loan = loans.get(loanId);
        // 2. Check if the loan exists AND if it is already returned (returnDate is not null)
        if(loan == null){
            System.out.println("Loan Not Found!");
            return false;
        }
        // 3. If valid, update the loan's returnDate
        loan.setReturnDate(returnDate);
        // 4. Check if the book was returned late and print an appropriate message
        if(returnDate.isAfter(loan.getDueDate())){
            System.out.println("Naah! you are a very late tweak bruhh..!!");
        } else{
            System.out.println("Yeah You're good to go brother!!");
        }
        // 5. Return true if successful, false otherwise
        return true;
    }
}
