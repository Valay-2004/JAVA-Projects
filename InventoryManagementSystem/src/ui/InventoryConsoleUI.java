package ui;

import model.Product;
import service.*;
import storage.CsvInventoryStorage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class InventoryConsoleUI {
    private final Inventory inventory;
    private final CsvInventoryStorage storage;
    private final Scanner scanner;

    public InventoryConsoleUI(Inventory inventory, CsvInventoryStorage storage){
        this.inventory = inventory;
        this.storage = storage;
        this.scanner = new Scanner(System.in);
    }

    public void run(){
        while(true){
            showMenu();
            int choice = getIntInput();
            if(!handleChoice(choice)){
                break;  //exit
            }
        }
        System.out.println("Goodbye!");
    }

    private void showMenu(){
        System.out.println("\n--- Inventory Management ---");
        System.out.print("1. Add Product\t\t | ");
        System.out.println("2. List All Products");
        System.out.print("3. View Products by Category\t\t | ");
        System.out.println("4. Sell Item (Reduce stock)");
        System.out.print("5. Add Stock\t\t | ");
        System.out.println("6. Exit");
        System.out.println("Choose an option: ");
    }

    private boolean handleChoice(int choice){
        try {
            switch (choice){
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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return true;
    }

    private void saveAndExit(){
        try{
            storage.saveInventory(inventory);
            System.out.println("Inventory saved.");
        } catch (Exception e){
            System.err.println("Failed to save: " + e.getMessage());
        }
    }
    private int getIntInput(){
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch(NumberFormatException e){
            return -1;
        }
    }

}
