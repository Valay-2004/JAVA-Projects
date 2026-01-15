import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private static final Path FILE = Paths.get("data/transactions.log");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static List<Transaction> findByAccount(String accountNumber){
        List<Transaction> list = new ArrayList<>();

        if(!Files.exists(FILE)) return list;

        try(BufferedReader br = Files.newBufferedReader(FILE)){
            String line;
            while((line = br.readLine()) != null){
                String[] p = line.split("\\s*\\|\\s*");
                String type = p[1].trim();
                String[] accounts = p[2].trim().split("->");

                String from = accounts[0].trim();
                String to = accounts[1].trim();

                if(from.equals(accountNumber) || to.equals(accountNumber)){
                    BigDecimal amount = new BigDecimal(p[3].trim());

                    Transaction txn = new Transaction(
                            type,
                            from,
                            to,
                            amount
                    );

                    list.add(txn);
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        return list;
    }
}
