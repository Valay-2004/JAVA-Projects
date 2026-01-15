import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private static final Path DATA_DIR;

    static {
        try {
            DATA_DIR = StorageUtil.ensureDataDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Path FILE = DATA_DIR.resolve("accounts.db");

    public static void save(Account account) {
        Map<String, Account> all = loadAll();
        all.put(account.getAccountNumber(), account);

        // Write data
        try {
            Files.write(
                    FILE,
                    buildFileContent(all).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to save accounts", e);
        }
    }

    // This method should be separate, not nested
    private static String buildFileContent(Map<String, Account> accounts) { // Changed parameter name
        StringBuilder sb = new StringBuilder();
        for(Account acc : accounts.values()){ // Changed from 'all' to 'accounts'
            sb.append(acc.getAccountNumber()).append(" | ")
                    .append(acc.getAccHolderName()).append(" | ")
                    .append(acc.getBalance()).append(" | ")
                    .append(acc.getAccountType())
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static Map<String, Account> loadAll() {
        Map<String, Account> map = new HashMap<>();

        if (!Files.exists(FILE)) return map; // Use Files.exists instead of File

        try (BufferedReader br = Files.newBufferedReader(FILE)) { // Use NIO consistently
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\s*\\|\\s*");

                String accountNumber = p[0];
                String accountHolderName = p[1];
                BigDecimal balance = new BigDecimal(p[2]);
                Account.AccountType accountType = Account.AccountType.valueOf(p[3]);

                // Make sure this matches your Account constructor parameters
                Account acc = new Account(
                        accountNumber,     // Second parameter
                        accountHolderName, // Third parameter
                        balance,           // First parameter in your constructor
                        accountType        // Fourth parameter
                );
                map.put(accountNumber, acc);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load accounts", e);
        }

        return map;
    }
}