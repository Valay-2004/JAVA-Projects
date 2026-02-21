import model.*;

import service.Inventory;
import storage.InventoryFileStorage;

import java.io.Serializable;
import java.math.BigDecimal;

public class IMSApp implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final InventoryFileStorage storage = new InventoryFileStorage();

    public static void start() {
        Inventory inv;
        try {
            inv = storage.loadInventory(); // Load existing data
            System.out.println("Loaded existing inventory.");
        } catch (Exception e) {
            System.err.println("Failed to load inventory: " + e.getMessage());
            inv = new Inventory(); // fallback create new Inventory
        }

        // Add demo data ONLY if empty (optional)
        if (inv.getAllProducts().isEmpty()) setupDemoData(inv);

        // show data
        System.out.println("All products");
        for (Product p : inv.getAllProducts())
            System.out.println("- " + p.getName() + " (Stock: " + p.getStock() + ")");

        // Save change
        try {
            inv.reduceStock("P-100", 1);
            storage.saveInventory(inv); // Persist changes
            System.out.println("Inventory saved.");
        } catch (Exception e) {
            System.err.println("Failed to save: " + e.getMessage());
        }
    }

    private static void setupDemoData(Inventory inv) {
        try {

            inv.addCategory(new Category("CAT-1", "Electronics"));
            inv.addSupplier(new Supplier("SUP-1", "TechCo", "tech@example.com"));
            inv.addProduct(new Product("P-100", "Laptop", new BigDecimal("59999"), 10, "SUP-1", "CAT-1"));
        } catch (Exception e) {
            System.err.println("Demo setup failed: " + e.getMessage());
        }
    }
}
