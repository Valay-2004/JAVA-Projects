package ui;

import service.Inventory;
import storage.CsvInventoryStorage;

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

    }
}
