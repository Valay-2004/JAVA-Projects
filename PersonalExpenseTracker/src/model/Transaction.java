package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private final String id;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String categoryId;
    private final TransactionType type;
    private final String note;


    // constructor
    public Transaction(String id, BigDecimal amount, LocalDate date, String categoryId, TransactionType type, String note) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.categoryId = categoryId;
        this.note = note;

    }

    // getters
    public String getId() {
        return id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    // isValid() method
    public boolean isValid(BigDecimal amount) {
        return amount != null
                && amount.compareTo(BigDecimal.ZERO) >= 0
                && date != null
                && categoryId != null
                && type != null;
    }

    // overridden method for toString()
    @Override
    public String toString(){
        return "Transaction{" + "id='" + id + '\'' + ", amount=" + amount +
                ", date=" + date + ", categoryId='" + categoryId + '\'' +
                ", type=" + type + '}';
    }

}
