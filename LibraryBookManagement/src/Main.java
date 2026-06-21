import model.*;

void main(){
    // Start of the new project
    // Todo: start modeling the outline of the project

    Author author0 = new Author("auth-100", "F. Scott Fitzgerald");
    Author author1 = new Author("auth-101", "Le Grand Meaulnes");
    Book newBook = new Book(
            "123",
            "The Great Gatsby",
            "9780743273565",
            List.of("auth-100", "auth-101")
    );

    Patron patron = new Patron("001", "Qwen", "qwen@qwen.com", 10);

    Loan loan = new Loan("00001", "123", "001", LocalDate.parse("2026-06-21"), LocalDate.parse("2026-06-24"), LocalDate.parse("2026-06-30"));


    System.out.println("------------------------------------------");
    System.out.println("Authors: ");
    System.out.println(author0.getId() + " " + author0.getName());
    System.out.println(author1.getId() + " " + author1.getName());

    System.out.println("---------------------------------------");
    System.out.println("Books: ");
    System.out.println("Title: " + newBook.getTitle() + ", ISBN: " + newBook.getIsbn());

    System.out.println("---------------------------------------");
    System.out.println("Patrons: ");
    System.out.println("Patrons id: " + patron.getId() + ", Name: " + patron.getName());

    System.out.println("---------------------------------------");
    System.out.println("Loans: ");
    System.out.println("Loan id: " + loan.getId() + ", Patron ID: " + loan.getPatronId() +", Loan date: " + loan.getLoanDate() + ", return date: " + loan.getReturnDate());

    System.out.println("--------------------------------------");

    System.out.println("I don't know how to automate all this so I manually console logged everything!");
}