import java.util.Scanner;

public class BackupModeSelector {

    public String selectMode() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Choose backup mode: ");
            System.out.println("1. Folder [incremental sync]");
            System.out.println("2. ZIP archive");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();
            switch (input){
                case "1" : return "folder";
                case "2" : return "zip";
                default:
                    System.out.println(" ⚠ Invalid input. Please enter 1 or 2.");
            }
        }
    }
}
