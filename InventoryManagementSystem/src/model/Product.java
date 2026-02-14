package model;

import java.math.BigDecimal;

public class Product {
    private String id;
    private String name;
    private BigDecimal price;
    private int stock;
    private String supplierId;
    private String categoryId;

    public Product(String id, String name, BigDecimal price, int stock, String supplierId, String categoryId){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    // setters
    // reducing stocks
    public void reduceStock(int amount){
        if(amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if(this.stock < amount) throw new IllegalStateException("Insufficient stock");
        this.stock -= amount;
    }
    // adding stocks
    public void addStock(int amount){
        if(amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.stock += amount;
    }

}
