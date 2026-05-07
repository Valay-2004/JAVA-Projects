package ui;

import model.*;
import model.dto.BudgetStatus;
import repository.*;
import service.BudgetService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final TransactionRepository transactionRepo;
    private final CategoryRepository categoryRepo;
    private final BudgetRepository budgetRepo;
    private final BudgetService budgetService;
    private final Scanner scanner;

    public Menu(TransactionRepository tRepo, CategoryRepository cRepo, BudgetRepository bRepo) {
        this.transactionRepo = tRepo;
        this.categoryRepo = cRepo;
        this.budgetRepo = bRepo;
        this.budgetService = new BudgetService(tRepo, cRepo, bRepo);
        this.scanner = new Scanner(System.in);
    }

    // 🚀 Main menu loop
    public void start() {
        System.out.println("""
            
            ╔═══════════════════════════════════════════╗
            ║     💰 Personal Expense Tracker 💰        ║
            ╚═══════════════════════════════════════════╝
            """);

        boolean running = true;
        while (running) {
            displayMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addTransactionMenu();
                case "2" -> viewCategoriesMenu();
                case "3" -> addCategoryMenu();
                case "4" -> addBudgetMenu();
                case "5" -> viewBudgetStatusMenu();
                case "6" -> generateReportMenu();
                case "7" -> {
                    System.out.println("\n👋 Thank you for using Expense Tracker. Goodbye!");
                    running = false;
                }
                default -> System.out.println("\n❌ Invalid option. Please try again.\n");
            }
        }
        scanner.close();
    }

    // 📋 Display main menu
    private void displayMainMenu() {
        System.out.println("""
            
            ──────────── 📋 Main Menu ────────────
            1. ➕ Add Transaction
            2. 📂 View Categories
            3. 🏷️ Add New Category
            4. 💰 Set Budget
            5. 📊 Check Budget Status
            6. 📈 Generate Monthly Report
            7. 🚪 Exit
            ─────────────────────────────────────
            """);
        System.out.print("Enter your choice (1-7): ");
    }

    // ➕ Add Transaction
    private void addTransactionMenu() {
        System.out.println("\n─── ➕ Add New Transaction ───");

        // Get amount
        BigDecimal amount = getValidAmount("Enter amount: ");

        // Get type
        System.out.print("Type (1=EXPENSE, 2=INCOME): ");
        String typeChoice = scanner.nextLine().trim();
        TransactionType type = typeChoice.equals("1") ? TransactionType.EXPENSE : TransactionType.INCOME;

        // Get category
        String categoryId = selectCategory("Select category ID: ");
        if (categoryId == null) return;

        // Get date
        LocalDate date = getValidDate("Enter date (YYYY-MM-DD): ");

        // Get note
        System.out.print("Note (optional): ");
        String note = scanner.nextLine().trim();

        // Create and save
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                amount,
                date,
                categoryId,
                type,
                note.isEmpty() ? null : note
        );

        transactionRepo.saveTransaction(transaction);
        System.out.println("\n✅ Transaction saved successfully!\n");
    }

    // 📂 View Categories (with tree visualization)
    private void viewCategoriesMenu() {
        System.out.println("\n─── 📂 Category Tree ───");
        List<Category> allCats = categoryRepo.loadCategories();

        // Find root categories (no parent)
        List<Category> roots = allCats.stream()
                .filter(c -> c.getParentId() == null || c.getParentId().isEmpty())
                .toList();

        for (Category root : roots) {
            printCategoryTree(root, allCats, 0);
        }
        System.out.println();
    }

    // Recursive tree printer
    private void printCategoryTree(Category category, List<Category> allCats, int depth) {
        String indent = "  ".repeat(depth);
        String icon = category.getType() == CategoryType.SYSTEM ? "🔒" : "🏷️";
        System.out.println(indent + icon + " " + category.getName() + " (" + category.getId() + ")");

        // Find and print children
        allCats.stream()
                .filter(c -> category.getId().equals(c.getParentId()))
                .forEach(child -> printCategoryTree(child, allCats, depth + 1));
    }

    // 🏷️ Add New Category
    private void addCategoryMenu() {
        System.out.println("\n─── 🏷️  Add New Category ───");

        System.out.print("Category name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Name cannot be empty.");
            return;
        }

        // Optional parent
        System.out.print("Parent category ID (leave empty for root): ");
        String parentId = scanner.nextLine().trim();
        if (!parentId.isEmpty()) {
            // Validate parent exists
            boolean parentExists = categoryRepo.loadCategories().stream()
                    .anyMatch(c -> c.getId().equals(parentId));
            if (!parentExists) {
                System.out.println("❌ Parent category not found.");
                return;
            }
        }

        Category category = new Category(
                UUID.randomUUID().toString(),
                name,
                parentId.isEmpty() ? null : parentId,
                CategoryType.USER
        );

        boolean added = categoryRepo.addCategory(category);
        if (added) {
            System.out.println("\n✅ Category added successfully!\n");
        } else {
            System.out.println("\n❌ Failed to add category (duplicate ID?).\n");
        }
    }

    // 💰 Set Budget
    private void addBudgetMenu() {
        System.out.println("\n─── 💰 Set New Budget ───");

        String categoryId = selectCategory("Select category to budget: ");
        if (categoryId == null) return;

        BigDecimal limit = getValidAmount("Enter budget limit: ");
        LocalDate startDate = getValidDate("Enter start date (YYYY-MM-DD): ");
        LocalDate endDate = getValidDate("Enter end date (YYYY-MM-DD): ");

        // Validate date range
        if (endDate.isBefore(startDate)) {
            System.out.println("❌ End date cannot be before start date.");
            return;
        }

        Budget budget = new Budget(
                UUID.randomUUID().toString(),
                categoryId,
                limit,
                startDate,
                endDate
        );

        boolean added = budgetRepo.addBudget(budget);
        if (added) {
            System.out.println("\n✅ Budget created successfully!\n");
        } else {
            System.out.println("\n❌ Failed to create budget.\n");
        }
    }

    // 📊 Check Budget Status
    private void viewBudgetStatusMenu() {
        System.out.println("\n─── 📊 Budget Status ───");

        List<Budget> budgets = budgetRepo.loadBudgets();
        if (budgets.isEmpty()) {
            System.out.println("No budgets found. Create one first!");
            return;
        }

        System.out.println("Available budgets:");
        for (int i = 0; i < budgets.size(); i++) {
            Budget b = budgets.get(i);
            System.out.printf("%d. %s (%s - %s)%n",
                    i + 1, b.getId(), b.getStartDate(), b.getEndDate());
        }

        System.out.print("\nSelect budget number: ");
        String choice = scanner.nextLine().trim();
        try {
            int index = Integer.parseInt(choice) - 1;
            if (index >= 0 && index < budgets.size()) {
                BudgetStatus status = budgetService.checkBudgetStatus(budgets.get(index).getId());
                if (status != null) {
                    System.out.println("\n" + status + "\n");
                }
            } else {
                System.out.println("❌ Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input.");
        }
    }

    // 📈 Generate Monthly Report
    private void generateReportMenu() {
        System.out.println("\n─── 📈 Monthly Budget Report ───");

        System.out.print("Enter year (YYYY): ");
        int year = getValidInt();
        System.out.print("Enter month (1-12): ");
        int month = getValidInt();

        YearMonth yearMonth = YearMonth.of(year, month);
        List<BudgetStatus> report = budgetService.generateMonthlyReport(yearMonth);

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.printf("║  📊 Budget Report: %s %-20s ║%n",
                yearMonth.getMonth(), yearMonth.getYear());
        System.out.println("╠════════════════════════════════════════════╣");

        if (report.isEmpty()) {
            System.out.println("║  No budgets found for this month.          ║");
        } else {
            for (BudgetStatus status : report) {
                String line = "║  " + status.toString().replaceAll("\n", " ");
                // Truncate long lines
                if (line.length() > 45) {
                    line = line.substring(0, 42) + " ║";
                } else {
                    line = line + " ".repeat(45 - line.length()) + "║";
                }
                System.out.println(line);
            }
        }
        System.out.println("╚════════════════════════════════════════════╝\n");
    }

    // ─── Helper Methods ───

    private String selectCategory(String prompt) {
        List<Category> allCats = categoryRepo.loadCategories();
        if (allCats.isEmpty()) {
            System.out.println("❌ No categories available. Create one first!");
            return null;
        }

        System.out.println("\nAvailable categories:");
        for (Category c : allCats) {
            System.out.printf("  - %s (%s)%n", c.getName(), c.getId());
        }

        System.out.print("\n" + prompt);
        String categoryId = scanner.nextLine().trim();

        // Validate
        boolean exists = allCats.stream().anyMatch(c -> c.getId().equals(categoryId));
        if (!exists) {
            System.out.println("❌ Category not found.");
            return null;
        }
        return categoryId;
    }

    private BigDecimal getValidAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                BigDecimal amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("❌ Amount cannot be negative.");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid amount. Please enter a number.");
            }
        }
    }

    private LocalDate getValidDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date. Use YYYY-MM-DD format.");
            }
        }
    }

    private int getValidInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid number. Try again: ");
            }
        }
    }
}