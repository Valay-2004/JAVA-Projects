package ui;

import model.Category;
import model.Product;
import model.Supplier;
import service.*;
import storage.CsvInventoryStorage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // validate category
        System.out.print("Enter CategoryId: ");
        String categoryId = scanner.nextLine();
        checkCategories(categoryId);

        // Validate Supplier Id
        System.out.print("Enter SupplierId: ");
        String supplierId = scanner.nextLine();
        checkSuppliers(supplierId);

        // Get stock & price
        System.out.println("Enter Initial Stock quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine();     // consume newline

        System.out.println("Enter Price for the given Product: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());  // it's safer than nextBigDecimal

        Product product = new Product(id, name, price, stock, supplierId, categoryId);
        inventory.addProduct(product);

    }

    // view the Product by category
    private void viewByCategory() throws InvalidProductException {
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        List<Product> productList = inventory.getProductByCategory(category);
        for (Product p : productList) {
            System.out.println(p.toString());
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

    // category checker
    private void checkCategories(String categoryId){
        while (!inventory.getCategories().containsKey(categoryId)) {
            System.out.println("Invalid Category ID: '" + categoryId + "'");
            if (inventory.getCategories().isEmpty()) {
                System.out.println("No categories exits yet.");
            } else {
                System.out.println("Currently available categories are: ");
                inventory.getCategories().values().forEach(c ->
                        System.out.println("  - " + c.getId() + " (" + c.getName() + ")")
                );
            }

            // Ask the user if they would like to add new category
            System.out.print("Would you like to add a new category? [Y/n] (press Enter = Yes): ");
            String addOrNot = scanner.nextLine().toLowerCase().trim();
            if(addOrNot.isEmpty() || addOrNot.charAt(0) == 'y') {
                System.out.print("Enter category ID: ");
                String catId = scanner.nextLine();
                // check if the category already exists
                if(inventory.getCategories().containsKey(catId)) {
                    System.out.println("Category ID already exists. Using the existing one.");
                    categoryId = catId;
                    continue; // will exit the while loop on next check
                }
                System.out.print("Enter name of the category: ");
                String catName = scanner.nextLine();

                if(catId.isEmpty() || catName.isEmpty()) {
                    System.out.println("Category ID or name cannot be empty. Try again.");
                    continue;
                }
                Category category = new Category(catId, catName);
                inventory.addCategory(category);
                System.out.println("✓ New Category '" + catId + "' (" + catName + ") added successfully!");

                categoryId = catId;
            } else {
                System.out.println("Enter a different category ID (or just press Enter to cancel): ");
                String newAttempt = scanner.nextLine().trim();
                if(newAttempt.isEmpty()){
                    System.out.println("Product addition cancelled (no valid category).");
                    return;
                }
                categoryId = newAttempt;
            }
            System.out.println("Using category: " + categoryId + " (" + inventory.getCategories().get(categoryId).getName() + ")");
        }
    }

    // supplier checker
    private void checkSuppliers(String supplierId){
        while (!inventory.getSuppliers().containsKey(supplierId)) {
            System.out.println("Invalid Supplier ID: '" + supplierId + "'");
            if (inventory.getSuppliers().isEmpty()) {
                System.out.println("No suppliers exist yet!");
            } else {
                System.out.println("Currently available suppliers are: ");
                inventory.getSuppliers().values().forEach(s ->
                        System.out.println("  - " + s.getId() + " (" + s.getName() + ")")
                );
            }

            // Asking the user if they would like to add new supplier
            System.out.print("Would you like to add new Supplier [Y/n] (press Enter = Yes): ");
            String addOrNot = scanner.nextLine().toLowerCase().trim();
            if (addOrNot.isEmpty() || addOrNot.charAt(0) == 'y'){
                // ask for new supplier Id
                System.out.print("Enter new supplier ID: ");
                String newSupId = scanner.nextLine();
                // check if the newSupId is already present
                if(inventory.getSuppliers().containsKey(newSupId)){
                    // let them know that the supplier id is already available and will now be used for the given
                    System.out.println("Supplier ID already exist. Using the existing one.");
                    supplierId = newSupId;
                    continue;
                }
                // get the Supplier name and contact info
                System.out.print("Enter Supplier Name: ");
                String supplierName = scanner.nextLine();
                // send the user for email validation
                String supplierEmail = emailValidator(scanner);

                // check if either id,name,contact-info is empty
                if(newSupId.isEmpty() || supplierName.isEmpty()){
                    System.out.println("Supplier Id or Name cannot be empty. Please try again!");
                    continue;
                }

                // now let's create the new supplier
                Supplier supplier = new Supplier(newSupId, supplierName, supplierEmail);
                inventory.addSupplier(supplier);
                System.out.println("✓ New Supplier '" + supplierId + "' (Name: " + supplierName + " Email: " + supplierEmail + ") added successfully!");

                supplierId = newSupId;
            } else {
                System.out.println("Enter a different Supplier ID (or just press Enter to cancel): ");
                String newAttempt = scanner.nextLine().trim();
                if(newAttempt.isEmpty()){
                    System.out.println("Product addition cancelled (no valid category).");
                    return;
                }
                supplierId = newAttempt;
            }
            System.out.println("Using supplierID: " + supplierId + " (" + inventory.getSuppliers().get(supplierId).getName() + ")");
        }
    }

    // email validator
    private String emailValidator(Scanner scanner){
        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
       String emailAddress;

        do{
            // Ask for email
            System.out.print("Enter Email of the Supplier: ");
            emailAddress = scanner.nextLine().trim();
            // validate the entered email
            if(emailAddress.isEmpty()){
                System.out.println("Email cannot be empty. Please try again.");
                continue;
            }
            Matcher matcher = EMAIL_PATTERN.matcher(emailAddress);
            // check if the email matches our regex pattern
            if(!matcher.matches()){
                System.out.println("Invalid Email format. Example: (e.g., user@example.com):");
                System.out.println("Please try again. \n");
                continue;
            }
            // if we are here means the email is good to go with.
            System.out.println("Email '" + emailAddress + "' looks good.");
            return emailAddress;
        } while (true);
    }
}
