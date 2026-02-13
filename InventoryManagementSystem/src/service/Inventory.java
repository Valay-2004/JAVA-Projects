package service;

// imports here
import model.*;
import java.util.*;

public class Inventory {

    // Storage Collection (model fields)
    private Map<String, Product> productsMap;
    private Map<String, Category> categoryMap;
    private Map<String, Supplier> supplierMap;

    // Constructor with new instance of HashMaps
    public Inventory(){
        this.productsMap = new HashMap<>();
        this.categoryMap = new HashMap<>();
        this.supplierMap = new HashMap<>();
    }

    // methods to register categories & suppliers first
    public void addCategory(Category category){
        categoryMap.put(category.getId(), category);
    }
    public void addSupplier(Supplier supplier){
        supplierMap.put(supplier.getId(), supplier);
    }

}
