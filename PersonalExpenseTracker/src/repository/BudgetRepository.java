package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Budget;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetRepository {
    private static final String FILE_PATH = "data/budget.json";
    private Gson gson;

    public BudgetRepository(){
        this.gson = new Gson();
        new File("data").mkdirs();
    }

    // loadBudget() -- to load complete budget
    public List<Budget> loadBudgets(){
        Path path = Paths.get(FILE_PATH);
        if(!Files.exists(path)) return new ArrayList<>();

        // try with resource
        try(Reader reader = new FileReader(path.toFile())){             // read the file
            Type listType = new TypeToken<List<Budget>>() {}.getType(); // get a typetoken (gson)
            return gson.fromJson(reader, listType);                     // convert fromJson to List and return
        } catch (IOException e){
            System.err.println("Error while reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // save budget
    public void saveBudget(List<Budget> budgets){
        String budget = gson.toJson(budgets);
        atomicWrite(budget, FILE_PATH);
    }

    // AtomicWrite
    public void atomicWrite(String content, String path){
        Path target = Path.of(path);
        Path temp = Path.of(path + ".tmp");

        try{
            Files.createDirectories(target.getParent());
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(path + ".tmp"))){
                bw.write(content);
            } try{
                Files.move(temp, target, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e){
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            try {
                Files.deleteIfExists(temp);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Error while writing file: " + path, e);
        }
    }
}
