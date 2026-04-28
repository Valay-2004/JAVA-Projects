package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Category;
import model.CategoryType;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryRepository {
    private static final String FILE_PATH = "data/categories.json";
    private final Gson gson;


    public CategoryRepository() {
        this.gson = new Gson();
        new File("data").mkdirs();
        initializeDefaults();
    }

    // loading categories
    public List<Category> loadCategories(){
        List<Category> categoryList = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);
        // check for the file if exist
        if(!Files.exists(path)) return categoryList;

        try (Reader reader = new FileReader(path.toFile())) {
            Type listType = new TypeToken<List<Category>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Error loading categories: " + e.getMessage());
            return categoryList;
        }
    }

    // saving categories
    public void saveCategories(List<Category> categories){
        // dump category as JSON to write it into category.json atomically using atomicWrite
        // assuming that we are getting the List from loadAllCategories
        String json = gson.toJson(categories);
        atomicWrite(json, FILE_PATH);
    }

    // atomicWrite -- writing the sb to categories atomically
    public void atomicWrite(String content, String path){
        Path targetPath = Paths.get(path);
        Path tempPath = Paths.get(path + ".tmp");

        // try with resources and check if the directory exists
        try{
            Files.createDirectories(targetPath.getParent());

            // Updating file
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(path + ".tmp"))){
                bw.write(content);
            }
            try {
                Files.move(tempPath, targetPath, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e){
                Files.move(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            // cleanup for temp files
            try {
                Files.deleteIfExists(tempPath);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Failed to write file: " + path, e);
        }
    }

    // add a new category with validation
    public boolean addCategory(Category category){
        List<Category> all = loadCategories();
        //validate : no duplication of IDs
        if (all.stream().anyMatch(c -> c.getId().equals(category.getId()))) return false;
        // in above condition we're simply checking for any match already available in the category file
        // for the given category via stream

        // add & save category
        all.add(category);
        saveCategories(all);
        return true;
    }

    // delete a category (if available in the category file)
    public boolean deleteCategory(String id){
        List<Category> all = loadCategories();

        // find the category
        Category target = all.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);

        if(target == null) return false;

        // Stop deletion of SYSTEM categories
        if(target.getType() == CategoryType.SYSTEM){
            System.out.println("Cannot delete System Category! " + target.getName());
            return false;
        }

        // blocking if any category has children
        boolean hasChildren = all.stream().anyMatch(c -> id.equals(c.getParentId()));
        if(hasChildren){
            System.out.println("Cannot delete category with Children. Reassign or delete all children categories first!");
            return false;
        }

        // all safe here to delete now
        all.removeIf(c -> c.getId().equals(id));
        saveCategories(all);
        return true;
    }

    // Helper: get all children of a category (recursive)
    public List<Category> getChildren(String parentId, List<Category> allCategories){
        return allCategories.stream()
                .filter(c -> parentId.equals(c.getParentId()))
                .collect(Collectors.toList());
    }

    // Loading Default SYSTEM categories
    private void initializeDefaults(){
        Path path = Paths.get(FILE_PATH);
        if(Files.exists(path)) return;

        List<Category> defaults = Arrays.asList(
                new Category("cat-food", "Food", null, CategoryType.SYSTEM),
                new Category("cat-transport", "Transport", null, CategoryType.SYSTEM),
                new Category("cat-groceries", "Groceries", "cat-food", CategoryType.SYSTEM),
                new Category("cat-dining", "Dining Out", "cat-food", CategoryType.SYSTEM)
        );
        saveCategories(defaults);
    }
}
