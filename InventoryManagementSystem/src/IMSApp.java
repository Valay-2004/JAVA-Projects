import model.*;

import service.Inventory;
import storage.CsvInventoryStorage;
import ui.InventoryConsoleUI;


public class IMSApp {

    public static void start() {
        CsvInventoryStorage storage = new CsvInventoryStorage();
        Inventory inv;
        try {
            inv = storage.loadInventory(); // Load existing data
            System.out.println("Loaded existing inventory.");
        } catch (Exception e) {
            System.err.println("Failed to load inventory: " + e.getMessage());
            inv = new Inventory(); // fallback create new Inventory
        }

        // Launch the interactive ui (CLI for now)
        InventoryConsoleUI ui = new InventoryConsoleUI(inv, storage);
        ui.run();   // starts the menu loop
    }
}
