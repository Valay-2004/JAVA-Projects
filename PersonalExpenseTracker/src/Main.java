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
        BudgetService service = new BudgetService(txnRepo, catRepo, bRepo);

        // Create test budget (only if not exists)
        createTestBudgets(bRepo);

        // Create test transactions
        createTestTransactions(txnRepo);

        // TEST 1: Category tree (already working! ✅)
        List<Category> allCats = catRepo.loadCategories();
        List<String> foodTree = service.getAllDescendantCategoryIds("cat-food", allCats);
        System.out.println("Food category tree: " + foodTree);

        // TEST 2: Budget status
        System.out.println("\n--- Budget Status ---");
        BudgetStatus status = service.checkBudgetStatus("budget-food-may");
        if (status != null) {
            System.out.println(status);
        } else {
            System.out.println("⚠️ Budget 'budget-food-may' not found");
            // Debug: print all loaded budgets
            List<model.Budget> allBudgets = bRepo.loadBudgets();
            System.out.println("Loaded budgets: " + allBudgets);
        }

        // TEST 3: Monthly report
        System.out.println("\n--- May 2026 Report ---");
        List<BudgetStatus> report = service.generateMonthlyReport(YearMonth.of(2026, 5));
        if (report.isEmpty()) {
            System.out.println("No budgets found for May 2026");
        } else {
            report.forEach(System.out::println);
        }
    }

    private static void createTestBudgets(BudgetRepository repo) {
        // Only add if not already exists
        boolean exists = repo.loadBudgets().stream()
                .anyMatch(b -> "budget-food-may".equals(b.getId()));
        if (!exists) {
            model.Budget budget = new model.Budget(
                    "budget-food-may",
                    "cat-food",
                    new BigDecimal("500.00"),
                    LocalDate.of(2026, 5, 1),
                    LocalDate.of(2026, 5, 31)
            );
            repo.addBudget(budget);
            System.out.println("✅ Created test budget: budget-food-may");
        }
    }

    private static void createTestTransactions(TransactionRepository repo) {
        // Add sample expenses in the Food tree for May 2026
        addIfNew(repo, new Transaction(null, new BigDecimal("150.00"),
                LocalDate.of(2026, 5, 10), "cat-groceries", TransactionType.EXPENSE, "Groceries"));
        addIfNew(repo, new Transaction(null, new BigDecimal("200.00"),
                LocalDate.of(2026, 5, 15), "cat-dining", TransactionType.EXPENSE, "Restaurant"));
    }

    private static void addIfNew(TransactionRepository repo, Transaction t) {
        // Simple dedup: check if similar transaction exists (by note + date)
        boolean exists = repo.loadAllTransactions().stream()
                .anyMatch(tx -> tx.getNote().equals(t.getNote()) && tx.getParsedDate().equals(t.getParsedDate()));
        if (!exists) {
            repo.saveTransaction(t);
            System.out.println("✅ Added transaction: " + t.getNote());
        }
    }

}