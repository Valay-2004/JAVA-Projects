package service;

// imports here
import model.*;

import java.math.BigDecimal;
import java.util.*;

public class Inventory {

    // Storage Collection (model fields)
    private Map<String, Product> products;
    private Map<String, Category> categories;
    private Map<String, Supplier> suppliers;

    // Constructor with new instance of HashMaps
    public Inventory(){
        this.products = new HashMap<>();
        this.categories = new HashMap<>();
        this.suppliers = new HashMap<>();
    }

    // methods to register categories & suppliers first
    public void addCategory(Category category){
        categories.put(category.getId(), category);
    }
    public void addSupplier(Supplier supplier){
        suppliers.put(supplier.getId(), supplier);
    }

    // Method for adding Product
    public void addProduct(Product product) throws InvalidProductException{
        // TODO: Validate product != null
        if(product == null){
            throw new InvalidProductException("Product cannot be null!");
        }
        // TODO: Validate product ID is not already used
        if(products.containsKey(product.getId())){
            throw new InvalidProductException("Product ID '" + product.getId() + "' is already in use!");
        }
        // TODO: Validate category exists
        if(!categories.containsKey(product.getCategoryId())){
            throw new InvalidProductException("Category ID '" + product.getCategoryId() + "' does not exists!");
        }
        // TODO: Validate supplier exists
        if(!suppliers.containsKey(product.getSupplierId())){
            throw new InvalidProductException("Supplier ID '" + product.getSupplierId() + "' does not exists!");
        }
        // TODO: Validate price > 0 and stock >= 0
        if(product.getPrice().compareTo(BigDecimal.ZERO) > 0){
            throw new InvalidProductException("Product Price cannot be 0 or less than 0");
        }
        if(product.getStock() < 0) throw new InvalidProductException("Stock cannot be less than 0");

        // All checks passed
        products.put(product.getId(), product);
    }

}
