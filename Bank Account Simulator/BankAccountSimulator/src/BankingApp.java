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
            System.out.println("---------------------------------------------");

            // Check if the input is an integer
            System.out.print("Enter you choice: ");
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
                        System.out.println("Good Bye. Thanks for doing business with us!\n\t\t Have a Wonderful day.");
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

        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your initial balance: $");
        BigDecimal initialBalance = scanner.nextBigDecimal();
        System.out.println("Enter account type: ");

        System.out.println("Select account type: ");
        System.out.println("1. SAVINGS              2. CHECKING");
        System.out.println("3. BUSINESS_CHECKING    4. FIXED_DEPOSIT");

        int type = scanner.nextInt();
        scanner.nextLine();     // consume newline
        Account.AccountType accountType;
        switch (type){
            case 1 -> accountType = Account.AccountType.SAVINGS;
            case 2 -> accountType = Account.AccountType.CHECKING;
            case 3 -> accountType = Account.AccountType.BUSINESS_CHECKING;
            case 4 -> accountType = Account.AccountType.FIXED_DEPOSIT;
            default -> {
                System.out.println("Invalid choice, defaulting to SAVINGS");
                accountType = Account.AccountType.SAVINGS;
            }
        }

        bank.addAccount(name, accountType, initialBalance);

    }
    public void deposit(){
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter deposit amount: $");
        BigDecimal depositAmount = scanner.nextBigDecimal();
        scanner.nextLine();

        try{
            Account account = bank.getAccount(accountNumber);       // get account through Bank
            account.deposit(depositAmount);    // Call deposit on the account
            // log the transaction
            account.logTransaction(new Transaction("DEPOSIT", accountNumber, "BANK", depositAmount));

            System.out.println("Amount Deposited Successfully!");
        } catch (Exception e) {
            System.out.println("An Error occurred: " + e.getMessage());
        }
    }

    public void withdraw(){
        System.out.println("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter amount to withdraw: $");
        BigDecimal withdrawAmount = scanner.nextBigDecimal();
        scanner.nextLine();

        try {
            Account account = bank.getAccount(accountNumber);
            account.withdraw(withdrawAmount);
            // log the transaction
            account.logTransaction(new Transaction("WITHDRAW", accountNumber, "BANK", withdrawAmount));

            System.out.println("Amount withdrawn successfully!");
        } catch (Exception e) {
            System.out.println("An Error occurred: " + e.getMessage());
        }
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
