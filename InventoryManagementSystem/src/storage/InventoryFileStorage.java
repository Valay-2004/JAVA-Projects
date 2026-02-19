package storage;

import service.Inventory;

import java.io.*;

public class InventoryFileStorage {
    // static file name
    private static final String DEFAULT_FILE = "inventory.dat";

    // save Inventory method
    public void saveInventory(Inventory inventory) throws IOException{
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DEFAULT_FILE))){
            oos.writeObject(inventory);
        }
    }

    // load inventory method
    public Inventory loadInventory() throws IOException, ClassNotFoundException{
        File file = new File(DEFAULT_FILE);
        if(!file.exists()){
            // first run ? return empty inventory
            return new Inventory();
        }
        try(ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DEFAULT_FILE))){
            return (Inventory) ois.readObject();
        }
    }
}
