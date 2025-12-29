import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Transaction {
    BigDecimal transactionAmount;
    String transactionType;
    String fromAccount;
    String toAccount;
    private final LocalDateTime transactionTimeStamp;

    public Transaction(String transactionType, String fromAccount, String toAccount, BigDecimal transactionAmount) {
        this.transactionType = transactionType;
        this.transactionTimeStamp = LocalDateTime.now();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionTimeStamp() {
        return transactionTimeStamp;
    }

//    public void setTransactionTimeStamp(LocalDateTime transactionTimeStamp) {
//        this.transactionTimeStamp = transactionTimeStamp;
//    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }
}
