package FinalProjBimmuyag;

/*
  Final Project
  Group 3
  March 10, 2025
  Balunatse, Denver C.
  Bimmuyag, Ashel John D.
  Lavarias, Ginobili D.
  Manalili, Meyielle Kkairi B.
  Rivera, Sherlie O.
  Ugay, Antonio Yzmael P.
  Valdriz, Jake Ivan T.
 */

import java.util.Date;

public class BankAccounts implements TransactionLoggable, AccountVerifiable{
    private int accountNo; // 9 digits
    private String accountName;
    private double balance;
    private String status; // active or closed

    private java.util.Date dateCreated;
    private java.util.Date closingDate;
    private java.util.Date reopenDate;

    /**
     * Default constructor
     * Sets status to active and balance to 0
     */
    public BankAccounts() {
        this.reopenDate = null;
        this.status = "active";
        this.balance = 0;
    }

    /**
     * Constructor with account number and name
     * Sets status to active and balance to 0
     *
     * @param accountNo   Account number (9 digits)
     * @param accountName Account holder's name
     */
    public BankAccounts(int accountNo, String accountName) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.reopenDate = reopenDate;
        this.status = "active";
        this.balance = 0;
    }

    /**
     * Gets the account number
     * @return Account number
     */
    public int getAccountNo() {
        return accountNo;
    }

    /**
     * Gets the account name
     * @return Account holder's name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Gets the account status
     * @return Account status (active or closed)
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the account number
     * @param accountNo Account number to set
     */
    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * Sets the account name
     * @param accountName Account holder's name to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    // Add these interface methods

    @Override
    public void logTransaction(String transactionType, double amount, String description) {
        FileManager.logTransaction(this.accountNo, transactionType, amount, description);
    }

    @Override
    public boolean verifyAccount() {
        // Basic account verification
        return this.accountNo >= 100000000 && this.accountNo <= 999999999;
    }

    @Override
    public boolean isActive() {
        return this.status.equals("active");
    }

    /**
     * Deposits money into the account
     * @param amount Amount to deposit
     */
    // Update deposit method to use custom exceptions
    public void deposit(double amount) throws AccountClosedException, InvalidAmountException {
        if (this.status.equals("closed")) {
            throw new AccountClosedException("Cannot deposit to a closed account.");
        }

        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }

        this.balance += amount;
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Deposit successful. New balance: ₱" + this.balance);

        // Log the transaction
        this.logTransaction("Deposit", amount, "Regular deposit");
    }

    /**
     * Withdraws money from the account
     * @param amount Amount to withdraw
     */
    // Update withdraw method to use custom exceptions
    public void withdraw(double amount) throws AccountClosedException, InvalidAmountException, InsufficientFundsException {
        if (this.status.equals("closed")) {
            throw new AccountClosedException("Cannot withdraw from a closed account.");
        }

        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }

        if (amount > this.balance) {
            throw new InsufficientFundsException("Insufficient funds.");
        }

        this.balance -= amount;
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Withdrawal successful. New balance: ₱" + this.balance);

        // Log the transaction
        this.logTransaction("Withdrawal", amount, "Regular withdrawal");
    }
    /**
     * Returns the current balance
     * @return Current balance
     */
    public double inquireBalance() {
        return balance;
    }

    /**
     * Sets balance directly (used by subclasses)
     * @param balance Balance to set
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Sets status directly (used by subclasses)
     * @param status Status to set
     */
    protected void setStatus(String status) {
        this.status = status;
    }

    /**
     * Receives a transfer from another account
     * @param amount Amount to receive
     */
    public void receiveTransfer(double amount) {
        if (this.status.equals("closed")) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Cannot receive transfer to a closed account.");
            return;
        }

        this.balance += amount;

        // Log the transaction
        FileManager.logTransaction(this.accountNo, "Transfer In", amount, "Money received from another account");
    }

    /**
     * Closes the account and withdraws all funds
     */
    public void closeAccount() {
        if (status.equals("closed")) {
            System.out.println("Account is already closed.");
            return;
        }
        System.out.println("Withdrawing remaining balance of ₱" + balance);
        balance = 0;
        status = "closed";
        System.out.println("Account closed successfully.");
    }

    //OPENING THE CLOSED ACCOUNT
    /**
     * Reopens a closed account
     */
    public void reopenAccount() {
        if (status.equals("closed")) {
            status = "active";
        }
    }

// Add getter for reopenDate
        public Date getReopenDate() {
            return reopenDate;
        }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Account Number: ").append(accountNo).append("\n");
        sb.append("Account Name: ").append(accountName).append("\n");
        sb.append("Balance: ").append(balance).append("\n");
        sb.append("Status: ").append(status).append("\n");

        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        sb.append("Date Created: ").append(dateCreated != null ? dateFormat.format(dateCreated) : "N/A").append("\n");

        if (closingDate != null) {
            sb.append("Date Closed: ").append(dateFormat.format(closingDate)).append("\n");
        }

        if (reopenDate != null) {
            sb.append("Date Reopened: ").append(dateFormat.format(reopenDate)).append("\n");
        }

        return sb.toString();
    }
    }

