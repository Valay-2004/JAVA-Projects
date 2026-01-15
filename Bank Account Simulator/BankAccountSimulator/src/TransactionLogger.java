import java.io.IOException;
import java.nio.file.*;

public class TransactionLogger {
private static final Path DATA_DIR;

    static {
        try {
            DATA_DIR = StorageUtil.ensureDataDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final Path FILE_NAME = DATA_DIR.resolve("transactions.log");

    public static synchronized void log(Transaction transaction){
        try{
            Files.writeString(
                    FILE_NAME,
                    transaction.toLogString() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to log transaction", e);
        }
    }
}
