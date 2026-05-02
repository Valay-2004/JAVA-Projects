package service;

import model.Budget;
import model.Category;
import model.Transaction;
import model.TransactionType;
import model.dto.BudgetStatus;
import repository.BudgetRepository;
import repository.CategoryRepository;
import repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetService {
    private final TransactionRepository transactionRepo;
    private final CategoryRepository categoryRepo;
    private final BudgetRepository budgetRepo;

    public BudgetService(TransactionRepository tRepo, CategoryRepository cRepo, BudgetRepository bRepo) {
        this.transactionRepo = tRepo;
        this.categoryRepo = cRepo;
        this.budgetRepo = bRepo;
    }

    // public --> check status of a single budget
    public BudgetStatus checkBudgetStatus(String budgetId) {
        Budget budget = findBudgetById(budgetId);
        if (budget == null) return null;

        // get all relevant category Ids (including children)
        List<Category> allCats = categoryRepo.loadCategories();
        List<String> relevantCategoryIds = getAllDescendantCategoryIds(budget.getCategoryId(), allCats);

        // Filter transactions: matching category + date range + expense type
        List<Transaction> allTxns = transactionRepo.loadAllTransactions();
        BigDecimal spent = calculateSpent(allTxns, relevantCategoryIds, budget.getStartDate(), budget.getEndDate());

        return new BudgetStatus(budget, spent, budget.getLimit().subtract(spent));
    }

    // public --> generate monthly report for all budgets
    public List<BudgetStatus> generateMonthlyReport(YearMonth yearMonth) {
        List<Budget> budgets = budgetRepo.loadBudgets();
        return budgets.stream()
                .filter(b -> isBudgetInMonth(b, yearMonth.atEndOfMonth()))
                .map(b -> checkBudgetStatus(b.getId()))
                .collect(Collectors.toList());
    }

    // private helper --> recursive tree traversal
    private List<String> getAllDescendantCategoryIds(String parentId, List<Category> allCategories) {
        List<String> result = new ArrayList<>();
        result.add(parentId); // include tha parent itself

        // find direct children
        List<Category> children = allCategories.stream()
                .filter(c -> parentId.equals(c.getParentId()))
                .toList();

        // Recursively add grandchildren etc.
        for (Category child : children) {
            result.addAll(getAllDescendantCategoryIds(child.getId(), allCategories));
        }

        return result;
    }

    // private helper --> sum spending for given categories + date range
    private BigDecimal calculateSpent(List<Transaction> transactions,
                                      List<String> categoryIds,
                                      LocalDate startDate,
                                      LocalDate endDate) {
        return transactions.stream()
                .filter(t -> categoryIds.contains(t.getCategoryId()))
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .filter(t -> t.getParsedDate() != null && !t.getParsedDate().isBefore(startDate) && !t.getParsedDate().isAfter(endDate))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // private helper --> finding budget by Id
    private Budget findBudgetById(String id) {
        List<Budget> budgets = budgetRepo.loadBudgets();

        // return null if target not found
        return budgets.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // private --> does the given budget cover this month
    private boolean isBudgetInMonth(Budget b, LocalDate yearMonth) {
        if (b == null || yearMonth == null) return false;
        // define the boundaries of the target month
        YearMonth targetMonth = YearMonth.from(yearMonth);
        LocalDate firstDayOfMonth = targetMonth.atDay(1);
        LocalDate lastDayOfMonth = targetMonth.atEndOfMonth();

        // check for overlap b/w the budget range and the month range
        // overlap logic --> (StartA <= EndB) && (EndA >= StartB)
        return !b.getStartDate().isAfter(lastDayOfMonth) &&
                !b.getEndDate().isBefore(firstDayOfMonth);
    }
}