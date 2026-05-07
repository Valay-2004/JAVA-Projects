package model.dto;

import model.Budget;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BudgetStatus {
    private Budget budget;
    private BigDecimal spent;
    private BigDecimal remaining;

    public BudgetStatus(Budget budget, BigDecimal spent, BigDecimal remaining) {
        this.budget = budget;
        this.spent = spent;
        this.remaining = remaining;
    }

    // Getters and setters
    @Override
    public String toString() {
        BigDecimal limit = budget.getLimit();
        String bar = "█".repeat((spent.multiply((new BigDecimal("20"))
                .divide(limit, RoundingMode.HALF_UP)).intValue()));

        return String.format("""
                        ╔════════════════════════════════╗
                        ║ %-30s ║
                        ╠════════════════════════════════╣
                        ║ Spent:    $%-26s║
                        ║ Limit:    $%-26s║
                        ║ Remaining:$%-26s║
                        ║ Progress: %-20s ║
                        ╚════════════════════════════════╝
                        """,
                budget.getCategoryId(), spent, limit, remaining,
                bar + " " + spent + "/" + limit);
    }
}
