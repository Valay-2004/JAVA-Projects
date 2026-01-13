import exceptions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(String accountNumber, String holderName, Account.AccountType accountType, BigDecimal initialBalance) {
        Account account = new Account(initialBalance,accountNumber, holderName, accountType);
        accounts.put(accountNumber, account);
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        if(!accounts.containsKey(accountNumber)){
            throw new AccountNotFoundException("Account Number " + accountNumber + " was not found in our bank's records!");
        }
        return accounts.get(accountNumber);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) throws AccountNotFoundException, InsufficientFundsException {
        // 1. Get both account with null checks that throw exception
        if(fromAccountNumber == null || toAccountNumber == null || amount == null){
            throw new InvalidTransferException("Source, Destination, or Amount cannot be null!");
        }
        if(fromAccountNumber.equals(toAccountNumber)){
            throw new InvalidTransferException("Source and Destination address cannot be same!");
        }
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransferException("Transfer amount must be positive value");
        }
        Account fromAccount = getAccount(fromAccountNumber);    // source
        Account toAccount = getAccount(toAccountNumber);        // destination
        // withdraw
        fromAccount.withdraw(amount);
        // deposit
        toAccount.deposit(amount);
    }
}
