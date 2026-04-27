package repository;

import com.google.gson.Gson;
import model.Transaction;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private static final String FILE_PATH = "data/transactions.jsonl";
    private final Gson gson;

    public TransactionRepository() {
        this.gson = new Gson();
        // Ensure data directory exists
        new File("data").mkdirs();
    }


    // TODO: Implement saveTransaction(Transaction t)
    public void saveTransaction(Transaction t){
        List<Transaction> allTransactions = loadAllTransactions();
        allTransactions.add(t);

        // Convert all to JSONL string
        StringBuilder sb = new StringBuilder();
        for (Transaction trans : allTransactions) {
            sb.append(gson.toJson(trans)).append("\n");
        }

        atomicWrite(sb.toString(), "data/transactions.jsonl");
    }

    // TODO: Implement loadAllTransactions() -> List<Transaction>
    public List<Transaction> loadAllTransactions() {
        List<Transaction> transactionsList = new ArrayList<>();

        // parse file line by line and return the List of Transactions
        try (BufferedReader br = new BufferedReader(new FileReader("data/transaction.jsonl"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Transaction transaction = gson.fromJson(line, Transaction.class);
                transactionsList.add(transaction);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return transactionsList;
    }

    // TODO: Implement private atomicWrite(String content, String path)
    public void atomicWrite(String content, String path){
        // target path
        Path targetPath = Paths.get(path);
        // temp path
        Path tempPath = Paths.get(path + ".tmp");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path + ".tmp"))){
            bw.write(content);
            Files.move(tempPath, targetPath, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            // Cleanup: delete temp file if it exists but move failed
            try {
                Files.deleteIfExists(tempPath);
            } catch (IOException ex) {
                // Log but don't hide the original error
                e.addSuppressed(ex);
            }
            throw new RuntimeException("Failed to write file: " + path, e);
        }
    }
}