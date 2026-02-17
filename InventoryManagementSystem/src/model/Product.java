package model;

import service.InvalidProductException;

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
    public void reduceStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (this.stock < quantity) throw new IllegalStateException("Insufficient stock");
        this.stock -= quantity;
    }
    public void addStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (this.stock > Integer.MAX_VALUE - quantity) throw new IllegalStateException("Stock overflow");
        this.stock += quantity;
    }

}
