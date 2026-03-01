package ui;

import model.Product;
import service.*;
import storage.CsvInventoryStorage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class InventoryConsoleUI {
    private final Inventory inventory;
    private final CsvInventoryStorage storage;
    private final Scanner scanner;

    public InventoryConsoleUI(Inventory inventory, CsvInventoryStorage storage) {
        this.inventory = inventory;
        this.storage = storage;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            showMenu();
            int choice = getIntInput();
            if (!handleChoice(choice)) {
                break;  //exit
            }
        }
        System.out.println("Goodbye!");
    }

    private void showMenu() {
        System.out.println("\n\t\t------- Inventory Management -------");
        System.out.print("1. Add Product\t\t\t\t | ");
        System.out.println("2. List All Products");
        System.out.print("3. View Products by Category | ");
        System.out.println("4. Sell Item (Reduce stock)");
        System.out.print("5. Add Stock\t\t\t\t | ");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private boolean handleChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> listAllProducts();
                case 3 -> viewByCategory();
                case 4 -> reduceStock();
                case 5 -> addStock();
                case 6 -> {
                    saveAndExit();
                    return false; //signal to exit loop
                }
                default -> System.out.println("Invalid Option!");
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return true;
    }

    private void saveAndExit() {
        try {
            storage.saveInventory(inventory);
            System.out.println("Inventory saved.");
        } catch (Exception e) {
            System.err.println("Failed to save: " + e.getMessage());
        }
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // methods for the switch case in the HandleChoice
    // listing all products
    private void listAllProducts() {
        var products = inventory.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }
        System.out.println("All Products: ");
        for (var p : products) {
            System.out.printf("- %s (ID: %s, Stock: %d, Price: %s)%n",
                    p.getName(),
                    p.getId(),
                    p.getStock(),
                    p.getPrice()
            );
        }
    }

    // adding a product accordingly to the user
    private void addProduct() throws InvalidProductException {
        System.out.print("Enter ID for the product: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name of the product: ");
        String name = scanner.nextLine();
        System.out.print("Enter SupplierId: ");
        String supplierId = scanner.nextLine();
        System.out.print("Enter CategoryId: ");
        String categoryId = scanner.nextLine();

        // validate category
        if (!inventory.getCategories().containsKey(categoryId)) {
            System.out.println("Invalid Category ID: '" + categoryId + "'");
            if (inventory.getCategories().isEmpty()) {
                // Todo -- Implement method for adding categories first
                System.out.println("No categories exits. Add one first!");
            } else {
                System.out.println("Current available categories are: ");
                inventory.getCategories().values().forEach(c ->
                        System.out.println("  - " + c.getId() + " (" + c.getName() + ")")
                );
            }
            return; // abort early as category id not found/unavailable
        }

        // Validate Supplier Id
        if (!inventory.getSuppliers().containsKey(supplierId)) {
            System.out.println("Invalid Supplier ID: '" + supplierId + "'");
            if (inventory.getSuppliers().isEmpty()) {
                // Todo -- Implement method for adding suppliers first
                System.out.println("No suppliers exist. Add one first!");
            } else {
                System.out.println("Current available suppliers are: ");
                inventory.getSuppliers().values().forEach(s ->
                        System.out.println("  - " + s.getId() + " (" + s.getName() + ")")
                );
            }
        }

        // Get stock & price
        System.out.println("Enter Initial Stock quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine();     // consume newline

        System.out.println("Enter Price for the given Product: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());  // it's safer than nextBigDecimal

        // Todo add the creation of product in try-catch block
        Product product = new Product(id, name, price, stock, supplierId, categoryId);
        inventory.addProduct(product);
    }

    // view the Product by category
    private void viewByCategory() throws InvalidProductException {
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        List<Product> productList = inventory.getProductByCategory(category);
        for (Product p : productList) {
            System.out.println(p);
        }
    }

    // removing / decrementing stock quantity
    private void reduceStock() throws InvalidProductException {
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();
        System.out.println("Enter Quantity to be reduced: ");
        int quantity = scanner.nextInt();
        inventory.reduceStock(productId, quantity);
    }

    // adding stocks to the current value / incrementing of stock
    private void addStock() throws InvalidProductException {
        System.out.print("Enter Product Id: ");
        String productId = scanner.nextLine();
        System.out.print("Enter Quantity to add: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        inventory.addStock(productId, quantity);
    }
}
