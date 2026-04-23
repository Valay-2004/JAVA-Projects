package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Budget {
    private String id;
    private String categoryId;
    private BigDecimal limit;
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructor
    public Budget(String id, String categoryId, BigDecimal limit, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.categoryId = categoryId;
        this.limit = limit;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // getters
    public String getId(){return id;}
    public String getCategoryId(){return categoryId;}
    public BigDecimal getLimit(){return limit;}

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
