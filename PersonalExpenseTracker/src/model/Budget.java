package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Budget {
    private String id;
    private String categoryId;
    private BigDecimal limit;
    private LocalDate localDate = LocalDate.now();

    // Constructor
    public Budget(String id, String categoryId, BigDecimal limit){
        this.id = id;
        this.categoryId = categoryId;
        this.limit = limit;
    }

    // getters
    public String getId(){return id;}
    public String getCategoryId(){return categoryId;}
    public BigDecimal getLimit(){return limit;}
    public LocalDate getLocalDate() {
        return localDate;
    }

    //setter for localDate
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
