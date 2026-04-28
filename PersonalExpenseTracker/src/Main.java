import model.Transaction;
import model.TransactionType;
import repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args){
        // Main start of the project will be here
        // TODO: Start the system design and implement is little by little

        // Transaction Trial
        TransactionRepository repo = new TransactionRepository();

        // new transaction
        Transaction t = new Transaction(
                null, // auto-generate ID
                new BigDecimal("25.50"),
                LocalDate.now(),
                "cat-123",
                TransactionType.EXPENSE,
                "Lunch"
        );

        System.out.println("Please wait!!");
        System.out.println("Saving transaction...");
        repo.saveTransaction(t);

        System.out.println("Loading transaction...");
        List<Transaction> loaded = repo.loadAllTransactions();
        System.out.println("Loaded " + loaded.size() + " transactions");
        loaded.forEach(System.out::println);
    }
}