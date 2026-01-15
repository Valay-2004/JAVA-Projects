import exceptions.*;

import java.math.BigDecimal;
import java.util.Map;

public class Bank {

    private final Map<String, Account> accounts;

    public Bank() {
        this.accounts = AccountRepository.loadAll();
    }

    public static String generateNextAccountNumber() {
        Map<String, Account> all = AccountRepository.loadAll();
        long max = 100000; // Initial point
        for (String accNo : all.keySet()) {
            if (accNo.startsWith("ACC")) {
                long num = Long.parseLong(accNo.substring(3));
                max = Math.max(max, num);
            }
        }
        return "ACC" + (max + 1);
    }

    public String addAccount(String holderName, Account.AccountType accountType, BigDecimal initialBalance) {
        String accountNumber = generateNextAccountNumber(); // generates ACC100000
        Account account = new Account(accountNumber, holderName, initialBalance, accountType);
        accounts.put(accountNumber, account);
        AccountRepository.save(account);
        return accountNumber;
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account Not Found In Our Bank Records!");
        }
        return account;
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
