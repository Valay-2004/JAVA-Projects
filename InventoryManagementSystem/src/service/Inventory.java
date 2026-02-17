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
            throw new InvalidProductException("Category ID '" + product.getCategoryId() + "' does not exist!");
        }
        // TODO: Validate supplier exists
        if(!suppliers.containsKey(product.getSupplierId())){
            throw new InvalidProductException("Supplier ID '" + product.getSupplierId() + "' does not exist!");
        }
        // TODO: Validate price > 0 and stock >= 0
        if(product.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidProductException("Product price must be greater than zero!");
        }
        if(product.getStock() < 0) throw new InvalidProductException("Stock cannot be less than 0");

        // All checks passed
        products.put(product.getId(), product);
    }

    // Get all Products
    public List<Product> getAllProducts(){
        return new ArrayList<>(products.values());
    }

    // Find Product By ID (safe lookup):
    public Product getProductById(String id) throws ProductNotFoundException{
        if(id == null) throw new ProductNotFoundException("ID cannot be null!");
        // create p object to get product by id
        Product p = products.get(id);
        if(p == null) throw new ProductNotFoundException("Product with ID '" + id + "' not found");
        // return if not null
        return p;
    }

    // Updating of the stocks
    // get behaviors from Product call it here with new methods
    // add stock
// Inventory.java
    public void addStock(String productId, int quantity) throws InvalidProductException {
        Product p = getProductById(productId);
        try {
            p.addStock(quantity);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new InvalidProductException(e.getMessage());
        }
    }

    public void reduceStock(String productId, int quantity) throws InvalidProductException {
        Product p = getProductById(productId);
        try {
            p.reduceStock(quantity);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new InvalidProductException(e.getMessage());
        }
    }
}
