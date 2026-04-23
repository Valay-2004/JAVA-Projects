package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private String id;
    private BigDecimal amount;
    private LocalDate localDate = LocalDate.now();
    private String categoryId;

    public enum type{
        INCOME, EXPENSE
    }

    private String note;

    // constructor
    public Transaction(String id, BigDecimal amount, String categoryId, String note) {
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
        this.note = note;

    }

    // getters
public String getId(){
        return id;
    }
    public String getCategoryId(){
        return categoryId;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public String getNote(){ return note;}
    public LocalDate getLocalDate(){ return localDate;}


    // isValid() method
    public boolean isValid(BigDecimal amount){
        return amount.compareTo(BigDecimal.ZERO) >= 0 || localDate != null;
    }

}
