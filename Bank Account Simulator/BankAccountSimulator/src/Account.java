import java.math.BigDecimal;

public class Account {
    private BigDecimal balance;
    private String accHolderName;
    private String accountNumber;
    private AccountType accountType;

    public enum AccountType{
        SAVINGS,
        CHECKING,
        BUSINESS_CHECKING,
        FIXED_DEPOSIT
    }

    public Account(BigDecimal balance, String accountNumber, String accHolderName, AccountType accountType){
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.accHolderName = accHolderName;
        this.accountType = accountType;
    }

    // Getters and Setters
    public AccountType getAccountType(){
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public void setAccountType(AccountType accountType){
        this.accountType = accountType;
    }
}
