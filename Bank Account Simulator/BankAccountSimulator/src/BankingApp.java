import java.math.BigDecimal;
import java.util.Scanner;

public class BankingApp {
    private Bank bank;
    private final Scanner scanner;

    public BankingApp() {
        this.bank = new Bank();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("---------------------------------------------");
        System.out.println("\t\t💰 Welcome to Banking App 🏦\t");
        // the choice user will give in the while loop
        boolean isRunning = true;
        while(isRunning){
            System.out.println("---------------------------------------------");
            System.out.println("Choose from the given options ");
            System.out.println("0. EXIT");
            System.out.println("1. Create Account   2. Deposit Money");
            System.out.println("3. Withdraw Money   4. Transfer Money");
            System.out.println("5. View Balance     6. View Transactions");

            // Check if the input is an integer
            int choice = -1;
            if(scanner.hasNextInt()){
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1 -> addAccount();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> transfer();
                    case 5 -> balance();
                    case 6 -> transaction();
                    case 0 -> {
                        isRunning = false;
                        System.out.println("GoodBye. Thanks for doing business with us!\n\t Have a Wonderful day.");
                    }
                    default -> System.out.println("Invalid Option!");
                }

            }
        }
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void addAccount(){
        // coming soon
    }
    public void deposit(){
        //coming soon
    }
    public void withdraw(){
        // coming soon
    }
    public void transfer(){
        //coming soon
    }
    public void balance(){
        // coming soon
    }
    public void transaction(){
        //coming soon
    }
}
