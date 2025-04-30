package src.main.java.banking.services;

import banking.exceptions.InvalidAccountException;
import banking.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all accounts in the banking system
 */
public class AccountManager {
    private final List<Account> accounts;
    private static AccountManager instance;
    
    /**
     * Creates a new account manager
     */
    private AccountManager() {
        this.accounts = new ArrayList<>();
    }
    
    /**
     * Gets the singleton instance of the account manager
     *
     * @return the account manager instance
     */
    public static synchronized AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }
    
    /**
     * Adds an account to the system
     *
     * @param account the account to add
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    /**
     * Gets all accounts in the system
     *
     * @return an unmodifiable list of all accounts
     */
    public List<Account> getAllAccounts() {
        return Collections.unmodifiableList(accounts);
    }
    
    /**
     * Gets all active accounts in the system
     *
     * @return a list of active accounts
     */
    public List<Account> getActiveAccounts() {
        return accounts.stream()
                .filter(Account::isActive)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets all closed accounts in the system
     *
     * @return a list of closed accounts
     */
    public List<Account> getClosedAccounts() {
        return accounts.stream()
                .filter(account -> !account.isActive())
                .collect(Collectors.toList());
    }
    
    /**
     * Gets accounts by account holder name
     *
     * @param name the account holder name to search for
     * @return a list of matching accounts
     */
    public List<Account> getAccountsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String lowerCaseName = name.toLowerCase();
        return accounts.stream()
                .filter(account -> account.getAccountHolderName().toLowerCase().contains(lowerCaseName))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets an account by account number
     *
     * @param accountNumber the account number to search for
     * @return the matching account
     * @throws InvalidAccountException if no matching account is found
     */
    public Account getAccountByNumber(String accountNumber) throws InvalidAccountException {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new InvalidAccountException("Account number cannot be empty");
        }
        
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new InvalidAccountException(accountNumber, "Account not found"));
    }
    
    /**
     * Applies monthly interest to all investment accounts
     */
    public void applyMonthlyInterestToAllAccounts() {
        accounts.stream()
                .filter(account -> account.isActive() && account instanceof InvestmentAccount)
                .map(account -> (InvestmentAccount) account)
                .forEach(InvestmentAccount::applyInterest);
    }
    
    /**
     * Applies monthly interest to all credit card accounts
     */
    public void applyMonthlyInterestToCreditCards() {
        accounts.stream()
                .filter(account -> account.isActive() && account instanceof CreditCardAccount)
                .map(account -> (CreditCardAccount) account)
                .forEach(CreditCardAccount::applyMonthlyInterest);
    }
    
    /**
     * Gets all accounts of a specific type
     *
     * @param accountType the type of accounts to get
     * @return a list of accounts of the specified type
     */
    public List<Account> getAccountsByType(Class<? extends Account> accountType) {
        return accounts.stream()
                .filter(account -> accountType.isInstance(account))
                .collect(Collectors.toList());
    }
    
    /**
     * Resets monthly transaction counts for all checking accounts
     */
    public void resetMonthlyTransactionCounts() {
        accounts.stream()
                .filter(account -> account.isActive() && account instanceof CheckingAccount)
                .map(account -> (CheckingAccount) account)
                .forEach(CheckingAccount::resetMonthlyTransactionCount);
    }
    
    /**
     * Resets daily withdrawal amounts for all bank accounts
     */
    public void resetDailyWithdrawalAmounts() {
        accounts.stream()
                .filter(account -> account.isActive() && account instanceof BankAccount)
                .map(account -> (BankAccount) account)
                .forEach(BankAccount::resetDailyWithdrawalAmount);
    }
}
