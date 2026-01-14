import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private static final String FILE = "data/accounts.db";

    public static void save(Account account){
        Map<String, Account> all = loadAll();
        all.put(account.getAccountNumber(), account);

        try(PrintWriter pw = new PrintWriter(new FileWriter(FILE))){
            for(Account acc : all.values()){
                pw.println(
                        acc.getAccountNumber() + " | " +
                        acc.getAccHolderName() + " | " +
                        acc.getBalance()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Account> loadAll(){
        Map<String, Account> map = new HashMap<>();

        File file = new File(FILE);
        if(!file.exists()) return map;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");

                String accountNumber = p[0];
                String accountHolderName = p[1];
                BigDecimal balance = new BigDecimal(p[2]);
                Account.AccountType accountType = Account.AccountType.valueOf(p[3]);
                Account acc = new Account(
                        accountNumber,
                        accountHolderName,
                        balance,
                        accountType
                );
                map.put(accountNumber, acc);
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        return map;
    }
}
