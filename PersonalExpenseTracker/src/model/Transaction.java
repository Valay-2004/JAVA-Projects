package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    private final String id;
    private final BigDecimal amount;
//    private final LocalDate date;
    private final String categoryId;
    private final TransactionType type;
    private final String note;
    private final String date; // Store as "YYYY-MM-DD" string


    // constructor
    public Transaction(String id, BigDecimal amount, LocalDate date, String categoryId, TransactionType type, String note) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.amount = amount;
        this.date = date != null ? date.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
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


    public TransactionType getType() {
        return type;
    }

    // parsed date
    public LocalDate getParsedDate() {
        return date != null ? LocalDate.parse(date) : null;
    }

    // isValid() method
    public boolean isValid() {
        return this.amount != null
                && this.amount.compareTo(BigDecimal.ZERO) >= 0
                && this.date != null
                && this.categoryId != null
                && this.type != null;
    }

    // overridden method for toString()
    @Override
    public String toString(){
        return "Transaction{" + "id='" + id + '\'' + ", amount=" + amount +
                ", date=" + date + ", categoryId='" + categoryId + '\'' +
                ", type=" + type + '}';
    }

}
