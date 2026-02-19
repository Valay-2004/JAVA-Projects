import model.*;
import service.InvalidProductException;
import service.Inventory;

import java.math.BigDecimal;

public class IMSApp {
    public static void start() throws InvalidProductException {
        Inventory inv = new Inventory();

        inv.addCategory(new Category("CAT-1", "Electronics"));
        inv.addSupplier(new Supplier("SUP-1", "TechCo", "tech@example.com"));

        inv.addProduct(new Product("P-100", "Laptop", new BigDecimal("59999"), 10, "SUP-1", "CAT-1"));

        System.out.println("All products:");
        for (Product p : inv.getAllProducts()) {
            System.out.println("- " + p.getName() + " (Stock: " + p.getStock() + ")");
        }

        // Try reducing stock
        inv.reduceStock("P-100", 2);
        System.out.println("After sale: " + inv.getProductById("P-100").getStock());
    }
}
