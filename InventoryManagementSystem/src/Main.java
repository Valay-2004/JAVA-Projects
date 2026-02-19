import service.InvalidProductException;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------------------");
        System.out.print("\t\tProject name -- Inventory Management System\n");
        System.out.println("-------------------------------------------------------------------");
        try {
            IMSApp.start();
        } catch (InvalidProductException e) {
            System.err.println("Error during startup: " + e.getMessage());
            // Optionally: log, exit, or show user-friendly message
        }
    }
}