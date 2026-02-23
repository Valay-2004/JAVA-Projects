package storage;

import model.Product;
import service.InvalidProductException;
import service.Inventory;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvInventoryStorage {
    // static file name
    private static final String PRODUCTS_FILE = "data/products.csv";
    private static final String CATEGORIES_FILE = "data/categories.csv";
    private static final String SUPPLIERS_FILE = "data/suppliers.csv";


    // save Inventory method
    public void saveInventory(Inventory inventory) throws IOException{
        // Ensure data directory exists
        new File("data").mkdirs();

        saveProducts(inventory.getAllProducts());
        saveCategories(new ArrayList<>(inventory.getCategories().values()));
        saveSuppliers(new ArrayList<>(inventory.getSuppliers().values()));
    }

    // load inventory method
    public Inventory loadInventory() throws IOException{
        Inventory inv = new Inventory();

        loadCategories(inv);
        loadSuppliers(inv);
        loadProducts(inv);

        return inv;
    }
    private void saveProducts(List<Product> products) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
            writer.println("id,name,price,stock,supplierId,categoryId");
            for (Product p : products) {
                // In saveProducts():
                writer.printf("%s,%s,%s,%d,%s,%s%n",
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStock(),
                        p.getSupplierId(),
                        p.getCategoryId()
                );
            }
        }
    }


    // In CsvInventoryStorage.java
    private void loadProducts(Inventory inv) throws IOException {
        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) return; // skip if no data

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 6) {
                    Product p = new Product(
                            parts[0],  // id
                            parts[1],  // name
                            new BigDecimal(parts[2]),  // price
                            Integer.parseInt(parts[3]), // stock
                            parts[4],  // supplierId
                            parts[5]   // categoryId
                    );
                    // ⚠️ Critical: Add to inventory!
                    try {
                        inv.addProduct(p); // reuses your validation logic!
                    } catch (InvalidProductException e) {
                        System.err.println("Skipped invalid product: " + e.getMessage());
                    }
                }
            }
        }
    }
}
