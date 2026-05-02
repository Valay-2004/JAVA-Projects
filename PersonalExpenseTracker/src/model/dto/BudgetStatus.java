package model.dto;

import model.Budget;

import java.math.BigDecimal;

public class BudgetStatus {
    private final Budget budget;
    private final BigDecimal spent;
    private final BigDecimal remaining;

    public BudgetStatus(Budget budget, BigDecimal spent, BigDecimal remaining) {
        this.budget = budget;
        this.spent = spent;
        this.remaining = remaining;
    }

    // Getters and setters
    @Override
    public String toString() {
        return String.format("Budget[%s]: %s spent of $%s limit ($%s remaining)",
                budget.getCategoryId(), spent, budget.getLimit(), remaining);
    }
}
