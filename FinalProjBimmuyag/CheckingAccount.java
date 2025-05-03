package FinalProjBimmuyag;

/*
  Final Project
  Group 3
  April 5, 2025
  Balunatse, Denver C.
  Bimmuyag, Ashel John D.
  Lavarias, Ginobili D.
  Manalili, Meyielle Kkairi B.
  Rivera, Sherlie O.
  Ugay, Antonio Yzmael P.
  Valdriz, Jake Ivan T.
 */

public class CheckingAccount extends BankAccounts implements TransactionLoggable, AccountVerifiable{
    public double minimumBalance;

    /**
     * Default constructor
     */
    public CheckingAccount() {
        super();
        this.minimumBalance = 0;
    }

    /**
     * Constructor with account details
     * @param accountNo Account number
     * @param accountName Account holder's name
     * @param minimumBalance Minimum balance required
     */
    public CheckingAccount(int accountNo, String accountName, double minimumBalance) {
        super(accountNo, accountName);
        this.minimumBalance = minimumBalance;
    }

    /**
     * Gets the minimum balance required
     * @return Minimum balance
     */
    public double getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * Encashes a check 
     * @param amount Amount to encash
     */
    // Modify encashCheck to include transaction logging
    public void encashCheck(double amount) throws InvalidAmountException, InsufficientFundsException, AccountClosedException {
        if (getStatus().equals("closed")) {
            throw new AccountClosedException("Transaction cancelled. Account is closed.");
        }

        if (amount <= 0) {
            throw new InvalidAmountException("Invalid amount. Please enter a positive value.");
        }

        double newBalance = inquireBalance() - amount;
        if (newBalance < minimumBalance) {
            throw new InsufficientFundsException("Transaction cancelled. This would bring balance below the minimum of ₱" + minimumBalance);
        }

        super.withdraw(amount);
        logTransaction("Check Encashment", amount, "Check encashed");
        System.out.println("Check encashed successfully.");
    }

    /**
     * Overrides withdraw to prevent withdrawals
     */
    @Override
    public void withdraw(double amount) throws InvalidAmountException, AccountClosedException {
        throw new InvalidAmountException("Withdrawal is not allowed for Credit Card Accounts. Please use getCashAdvance instead.");
    }

    /**
     * Returns a string representation of the checking account
     * @return String with account details
     */
    @Override
    public String toString() {
        return super.toString() +
                "\nAccount Type: Checking Account" +
                "\nMinimum Balance: ₱" + minimumBalance;
    }
    // Add methods to implement TransactionLoggable
    @Override
    public void logTransaction(String transactionType, double amount, String description) {
        FileManager.logTransaction(getAccountNo(), transactionType, amount, description);
    }

    // Add methods to implement AccountVerifiable
    @Override
    public boolean verifyAccount() {
        return getStatus().equals("active");
    }
}