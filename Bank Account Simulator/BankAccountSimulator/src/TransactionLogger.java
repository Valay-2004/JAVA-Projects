import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TransactionLogger {
    private static final String FILE_NAME = "logs/transactions.log";

    public static void log(Transaction transaction){
        try(PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))){
            out.println(transaction.toLogString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to log transaction", e);
        }
    }
}
