package src.main.java.banking.models;

import banking.exceptions.*;
import banking.interfaces.AccountVerifiable;
import banking.interfaces.TransactionLoggable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Abstract base class for all account types
 */
public abstract class Account implements AccountVerifiable, TransactionLoggable {
    protected String accountNumber;
    protected String accountHolderName;
    protected double balance;
    protected LocalDate openingDate;
    protected LocalDate closingDate;
    protected boolean isActive;
    protected List<Transaction> transactions;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Creates a new account with the specified details
     *
     * @param accountHolderName the name of the account holder
     * @param initialDeposit the initial deposit amount
     */
    public Account(String accountHolderName, double initialDeposit) throws InvalidAmountException {
        if (initialDeposit < 0) {
            throw new InvalidAmountException(initialDeposit);
        }
        
        this.accountNumber = generateAccountNumber();
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.openingDate = LocalDate.now();
        this.isActive = true;
        this.transactions = new ArrayList<>();
        
        if (initialDeposit > 0) {
            logTransaction("Initial Deposit", initialDeposit, "Account opening deposit", balance);
        }
    }
    
    /**
     * Generates a unique account number
     *
     * @return a unique account number
     */
    protected String generateAccountNumber() {
        // Generate a random account number based on UUID to ensure uniqueness
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * Gets the account number
     *
     * @return the account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    
    /**
     * Gets the account holder's name
     *
     * @return the account holder's name
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    /**
     * Gets the current balance
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Gets the account opening date
     *
     * @return the opening date
     */
    public LocalDate getOpeningDate() {
        return openingDate;
    }
    
    /**
     * Gets the account closing date
     *
     * @return the closing date, or null if the account is still active
     */
    public LocalDate getClosingDate() {
        return closingDate;
    }
    
    /**
     * Checks if the account is active
     *
     * @return true if the account is active, false if it's closed
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Deposits money into the account
     *
     * @param amount the amount to deposit
     * @throws AccountClosedException if the account is closed
     * @throws InvalidAmountException if the amount is not positive
     */
    public void deposit(double amount) throws AccountClosedException, InvalidAmountException {
        if (!isActive) {
            throw new AccountClosedException();
        }
        
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        
        balance += amount;
        logTransaction("Deposit", amount, "Cash deposit", balance);
    }
    
    /**
     * Withdraws money from the account
     *
     * @param amount the amount to withdraw
     * @throws AccountClosedException if the account is closed
     * @throws InvalidAmountException if the amount is not positive
     * @throws InsufficientFundsException if there are not enough funds
     */
    public void withdraw(double amount) throws AccountClosedException, InvalidAmountException, InsufficientFundsException {
        if (!isActive) {
            throw new AccountClosedException();
        }
        
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        
        balance -= amount;
        logTransaction("Withdrawal", amount, "Cash withdrawal", balance);
    }
    
    /**
     * Transfers money to another account
     *
     * @param destinationAccount the account to transfer to
     * @param amount the amount to transfer
     * @throws AccountClosedException if either account is closed
     * @throws InvalidAmountException if the amount is not positive
     * @throws InsufficientFundsException if there are not enough funds
     * @throws InvalidAccountException if the destination account is invalid
     */
    public void transfer(Account destinationAccount, double amount) 
            throws AccountClosedException, InvalidAmountException, InsufficientFundsException, InvalidAccountException {
        if (!isActive) {
            throw new AccountClosedException();
        }
        
        if (destinationAccount == null) {
            throw new InvalidAccountException("Destination account does not exist");
        }
        
        if (!destinationAccount.isActive()) {
            throw new AccountClosedException("Destination account is closed");
        }
        
        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be positive");
        }
        
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        
        balance -= amount;
        logTransaction("Transfer Out", amount, 
                "Transfer to account " + destinationAccount.getAccountNumber(), balance);
        
        destinationAccount.receiveTransfer(this, amount);
    }
    
    /**
     * Receives a transfer from another account
     *
     * @param sourceAccount the account the transfer is from
     * @param amount the amount received
     */
    protected void receiveTransfer(Account sourceAccount, double amount) {
        balance += amount;
        logTransaction("Transfer In", amount, 
                "Transfer from account " + sourceAccount.getAccountNumber(), balance);
    }
    
    /**
     * Logs a transaction with the current timestamp
     *
     * @param transactionType the type of transaction
     * @param amount the amount involved
     * @param description the description of the transaction
     */
    protected void logTransaction(String transactionType, double amount, String description, double balanceAfter) {
        Transaction transaction = new Transaction(transactionType, amount, description, balanceAfter);
        transactions.add(transaction);
    }
    
    /**
     * Closes the account
     *
     * @throws AccountClosedException if the account is already closed
     * @throws InsufficientFundsException if the account has a negative balance
     */
    public void closeAccount() throws AccountClosedException, InsufficientFundsException {
        if (!isActive) {
            throw new AccountClosedException("Account is already closed");
        }
        
        if (balance < 0) {
            throw new InsufficientFundsException("Cannot close account with negative balance");
        }
        
        isActive = false;
        closingDate = LocalDate.now();
        logTransaction("Account Closed", 0, "Account closed with final balance of $" + balance, balance);
    }
    
    /**
     * Gets account details as a string
     *
     * @return a string containing account details
     */
    public String getAccountDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Account Number: ").append(accountNumber).append("\n");
        details.append("Account Holder: ").append(accountHolderName).append("\n");
        details.append("Account Type: ").append(getAccountType()).append("\n");
        details.append("Balance: $").append(String.format("%.2f", balance)).append("\n");
        details.append("Opening Date: ").append(openingDate.format(DATE_FORMATTER)).append("\n");
        details.append("Status: ").append(isActive ? "Active" : "Closed").append("\n");
        
        if (!isActive && closingDate != null) {
            details.append("Closing Date: ").append(closingDate.format(DATE_FORMATTER)).append("\n");
        }
        
        return details.toString();
    }
    
    /**
     * Gets the account type as a string
     *
     * @return the account type name
     */
    public abstract String getAccountType();
    
    @Override
    public void logTransaction(String transactionType, double amount, String description) {
        logTransaction(transactionType, amount, description, balance);
    }
    
    @Override
    public String getTransactionHistory() {
        StringBuilder history = new StringBuilder();
        history.append("Transaction History for Account ").append(accountNumber).append("\n");
        history.append("----------------------------------------\n");
        
        if (transactions.isEmpty()) {
            history.append("No transactions found\n");
        } else {
            for (Transaction transaction : transactions) {
                history.append(transaction.toString()).append("\n");
            }
        }
        
        return history.toString();
    }
    
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
    
    @Override
    public boolean verifyAccountDetails() {
        // Base verification logic - can be overridden by subclasses
        return accountNumber != null && !accountNumber.isEmpty() &&
               accountHolderName != null && !accountHolderName.isEmpty() &&
               openingDate != null;
    }
}
