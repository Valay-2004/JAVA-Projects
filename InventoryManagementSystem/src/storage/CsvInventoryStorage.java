package storage;

import model.Category;
import model.Product;
import model.Supplier;
import service.InvalidProductException;
import service.Inventory;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvInventoryStorage {
    // static file name
    private static final String PRODUCTS_FILE = "data/products.csv";
    private static final String CATEGORIES_FILE = "data/categories.csv";
    private static final String SUPPLIERS_FILE = "data/suppliers.csv";


    // save Inventory method
    public void saveInventory(Inventory inventory) throws IOException {
        // Ensure data directory exists
        new File("data").mkdirs();

        saveProducts(inventory.getAllProducts());
        saveCategories(new ArrayList<>(inventory.getCategories().values()));
        saveSuppliers(new ArrayList<>(inventory.getSuppliers().values()));
    }

    // load inventory method
    public Inventory loadInventory() throws IOException {
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

    private void saveCategories(List<Category> categories) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CATEGORIES_FILE))) {
            writer.println("id,name");
            for (Category c : categories) {
                // In saveCategories():
                writer.printf("%s,%s%n",
                        c.getId(),
                        c.getName());
            }
        }

    }

    private void loadCategories(Inventory inv) throws IOException {
        File file = new File(CATEGORIES_FILE);
        if (!file.exists()) return;  // skip if no data

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 2) {
                    Category c = new Category(
                            parts[0],   // id
                            parts[1]    // name
                    );

                    // Add to inventory!
                    inv.addCategory(c); //  no need for try-catch block
                }
            }
        }
    }

    private void saveSuppliers(List<Supplier> suppliers) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SUPPLIERS_FILE))) {
            writer.println("id,name,contactInfo");
            for (Supplier s : suppliers) {
                // In saveSuppliers():
                writer.printf("%s,%s,%s%n",
                        s.getId(),
                        s.getName(),
                        s.getContactInfo()
                );
            }
        }
    }

    private void loadSuppliers(Inventory inv) throws IOException {
        File file = new File(SUPPLIERS_FILE);
        if (!file.exists()) return; // skip if no data

        // run the loop to read all the suppliers
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 3) {
                    Supplier s = new Supplier(
                            parts[0],   // id
                            parts[1],   // name
                            parts[2]   // contactInfo
                    );

                    // Add to inv
                        inv.addSupplier(s); // no need for try-catch block
                }
            }
        }
    }
}
