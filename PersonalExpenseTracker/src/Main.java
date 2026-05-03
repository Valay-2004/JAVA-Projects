import model.*;
import model.dto.BudgetStatus;
import repository.BudgetRepository;
import repository.CategoryRepository;
import repository.TransactionRepository;
import service.BudgetService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Main start of the project will be here

        // Setup repositories
        TransactionRepository txnRepo = new TransactionRepository();
        CategoryRepository catRepo = new CategoryRepository();
        BudgetRepository bRepo = new BudgetRepository();
        createTestBudgets(bRepo);

        // Example: Add a $150 grocery expense in May
        Transaction grocery = new Transaction(
                null, // auto-ID
                new BigDecimal("150.00"),
                LocalDate.of(2026, 5, 10),
                "cat-groceries", // Child of cat-food!
                TransactionType.EXPENSE,
                "Weekly groceries"
        );
        txnRepo.saveTransaction(grocery);

        // setup service
        BudgetService service = new BudgetService(txnRepo, catRepo, bRepo);

        // TEST 1: Recursive category traversal
        List<Category> allCats = catRepo.loadCategories();
        List<String> foodTree = service.getAllDescendantCategoryIds("cat-food", allCats);
        System.out.println("Food category tree: " + foodTree);
        // expected: [cat-food, cat-groceries, cat-dining] (or similar)

        // TEST 2: Budget status calculation
        // first, ensure you have:
        // - A budget for "cat-food" with limit $500, date range Match 2024
        // - Transactions:
        //   * $150 on cat-groceries, March 5
        //   * $200 on cat-dining, March 15
        //   * $50 on cat-food (direct), March 20

        BudgetStatus status = service.checkBudgetStatus("budget-food-march");
        if (status != null) System.out.println("\nBudget Status: " + status);
        else System.out.println("\n⚠️ Budget not found. Create one first!");

        // TEST 3: Monthly report
        System.out.println("\nMay 2026 Budget Report: ");
        List<BudgetStatus> report = service.generateMonthlyReport(YearMonth.of(2026, 5));
        report.forEach(System.out::println);
    }
    // Add this method to Main.java
    private static void createTestBudgets(BudgetRepository budgetRepo) {
        // Create a budget for Food category, valid for May 2026, limit $500
        Budget foodBudget = new Budget(
                "budget-food-may",           // ID
                "cat-food",                  // Category ID
                new BigDecimal("500.00"),    // Limit
                LocalDate.of(2026, 5, 1),    // Start date
                LocalDate.of(2026, 5, 31)    // End date
        );
        budgetRepo.addBudget(foodBudget);
        System.out.println("✅ Created test budget: budget-food-may");
    }

}