import model.Category;
import model.CategoryType;
import model.Transaction;
import model.TransactionType;
import repository.CategoryRepository;
import repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args){
        // Main start of the project will be here

//        // Transaction Trial
//        TransactionRepository repo = new TransactionRepository();
//
//        // new transaction
//        Transaction t = new Transaction(
//                null, // auto-generate ID
//                new BigDecimal("25.50"),
//                LocalDate.now(),
//                "cat-123",
//                TransactionType.EXPENSE,
//                "Lunch"
//        );
//
//        System.out.println("Please wait!!");
//        System.out.println("Saving transaction...");
//        repo.saveTransaction(t);
//
//        System.out.println("Loading transaction...");
//        List<Transaction> loaded = repo.loadAllTransactions();
//        System.out.println("Loaded " + loaded.size() + " transactions");
//        loaded.forEach(System.out::println);

        CategoryRepository catRepo = new CategoryRepository();
        //Load and print default cats
        List<Category> cats = catRepo.loadCategories();
        cats.forEach(c -> System.out.println(c.getName() + " (parent: " + c.getParentId() + ")"));

// First add
        Category gym1 = new Category("cat-gym-test", "Gym", null, CategoryType.USER);
        System.out.println("First add: " + catRepo.addCategory(gym1)); // true

// Second add with same ID
        Category gym2 = new Category("cat-gym-test", "Gym Duplicate", null, CategoryType.USER);
        System.out.println("Second add: " + catRepo.addCategory(gym2)); // false (duplicate ID)
    }
}