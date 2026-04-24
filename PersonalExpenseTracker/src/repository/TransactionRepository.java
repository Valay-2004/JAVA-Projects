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


    /*
    Implementation Hints:
    For saveTransaction:
        Convert Transaction to JSON: gson.toJson(transaction)
        Append + newline to file using FileWriter(true) (append mode)
        Better: Use atomicWrite to avoid partial writes
    For loadAllTransactions:
        Use BufferedReader to read line-by-line
    For each line: gson.fromJson(line, Transaction.class)
        Add to List<Transaction>
        Handle IOException and JsonSyntaxException
    For atomicWrite:
        Write content to filePath + ".tmp"
        Use Files.move(tempPath, targetPath, ATOMIC_MOVE)
     */
    // TODO: Implement saveTransaction(Transaction t)
    public void saveTransaction(Transaction t){
        String transactionJson = gson.toJson(t);

        // append newline to transaction.jsonl
        try(FileWriter fw = new FileWriter("data/transaction.jsonl", true);
        BufferedWriter bw = new BufferedWriter(fw)){
            bw.write(transactionJson);
            bw.newLine(); // add newline for jsonl format
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: Implement loadAllTransactions() -> List<Transaction>
    // TODO: Implement private atomicWrite(String content, String path)
}