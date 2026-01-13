import exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private BigDecimal balance;
    private String accHolderName;
    private String accountNumber;
    private AccountType accountType;
    // Store transaction
    private final List<Transaction> transactionList;
    private static final int MAX_TRANSACTIONS = 10;

    // Account types
    public enum AccountType {
        SAVINGS,
        CHECKING,
        BUSINESS_CHECKING,
        FIXED_DEPOSIT
    }

    public Account(BigDecimal balance, String accountNumber, String accHolderName, AccountType accountType) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.accHolderName = accHolderName;
        this.accountType = accountType;
        this.transactionList = new ArrayList<>();
    }

    // Getters and Setters
    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance.setScale(2, RoundingMode.HALF_UP);

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) throws InsufficientFundsException {
        // check if amount is greater than the current balance
        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException("Cannot withdraw " + amount + " from current balance of " + balance);
        }
        // if funds are enough >> amount <= balance
        balance = balance.subtract(amount);
        System.out.println("Successfully withdrew: " + amount);
    }

    public void logTransaction(Transaction transaction){
        this.transactionList.add(transaction);
    }
}
