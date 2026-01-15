import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final BigDecimal transactionAmount;
    private final String transactionType;
    private final String fromAccount;
    private final String toAccount;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final LocalDateTime transactionTimeStamp;

    public Transaction(String transactionType, String fromAccount, String toAccount, BigDecimal transactionAmount) {
        this.transactionType = transactionType;
        this.transactionTimeStamp = LocalDateTime.now();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionAmount = transactionAmount;
    }

    public String toLogString(){
        return getFormatterTimestamp() + " | " + transactionType + " | "
                + fromAccount + " -> " + toAccount + " | " + transactionAmount;
    }
    public String getFormatterTimestamp(){
        return transactionTimeStamp.format(formatter);
    }

}
