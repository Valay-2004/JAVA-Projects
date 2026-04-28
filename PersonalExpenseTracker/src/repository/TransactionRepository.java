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


    // DONE: Implement saveTransaction(Transaction t)
    public void saveTransaction(Transaction t){
        List<Transaction> allTransactions = loadAllTransactions();
        allTransactions.add(t);

        // Convert all to JSONL string
        StringBuilder sb = new StringBuilder();
        for (Transaction trans : allTransactions) {
            sb.append(gson.toJson(trans)).append("\n");
        }

        atomicWrite(sb.toString(), FILE_PATH);
    }

    // DONE: Implement loadAllTransactions() -> List<Transaction>
    public List<Transaction> loadAllTransactions() {
        List<Transaction> transactionsList = new ArrayList<>();

        // Check if the file exists in the FILE_PATH
        Path filePath = Paths.get(FILE_PATH);
        if(!Files.exists(filePath)) return transactionsList;

        // parse file line by line and return the List of Transactions
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // skip empty lines
                if (line.trim().isEmpty()) continue;

                Transaction transaction = gson.fromJson(line, Transaction.class);
                transactionsList.add(transaction);
            }
        } catch (IOException e) {
            System.err.println("Error reading transaction: " + e.getMessage());;
            // returning whatever we got instead of the crash
            return transactionsList;
        }

        return transactionsList;
    }

    // DONE: Implement private atomicWrite(String content, String path)
    public void atomicWrite(String content, String path){
        // target path
        Path targetPath = Paths.get(path);
        // temp path
        Path tempPath = Paths.get(path + ".tmp");

        try {
            // check if the directory exists for tmp file to exists
            Files.createDirectories(targetPath.getParent());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + ".tmp"))) {
                bw.write(content);
            }
            try{
                Files.move(tempPath, targetPath, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tempPath, targetPath, StandardCopyOption.ATOMIC_MOVE);
            }
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