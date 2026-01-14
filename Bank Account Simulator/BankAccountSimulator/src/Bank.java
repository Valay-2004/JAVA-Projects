import exceptions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts;
    private long nextAccountNumber = 100000; // starting number

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public String addAccount(String holderName, Account.AccountType accountType, BigDecimal initialBalance) {
        String accountNumber = "ACC" + nextAccountNumber++; // generates ACC100000
        Account account = new Account(accountNumber, holderName, initialBalance, accountType);
        accounts.put(accountNumber, account);
        return accountNumber;
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        if (!accounts.containsKey(accountNumber)) {
            throw new AccountNotFoundException("Account Number " + accountNumber + " was not found in our bank's records!");
        }
        return accounts.get(accountNumber);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) throws AccountNotFoundException, InsufficientFundsException, InvalidTransferException {
        // 1. Get both account with null checks that throw exception
        if (fromAccountNumber == null || toAccountNumber == null || amount == null) {
            throw new InvalidTransferException("Source, Destination, or Amount cannot be null!");
        }
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new InvalidTransferException("Source and Destination address cannot be same!");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransferException("Transfer amount must be positive value");
        }
        Account fromAccount = getAccount(fromAccountNumber);    // source
        Account toAccount = getAccount(toAccountNumber);        // destination
        // withdraw
        fromAccount.withdraw(amount);
        fromAccount.logTransaction(new Transaction("TRANSFER_OUT", fromAccount.getAccountNumber(), toAccount.getAccountNumber(), amount));
        // deposit
        toAccount.deposit(amount);
        toAccount.logTransaction(new Transaction("TRANSFER_IN", toAccount.getAccountNumber(), fromAccount.getAccountNumber(), amount));
    }

}
