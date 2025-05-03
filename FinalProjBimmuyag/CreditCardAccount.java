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

public class CreditCardAccount extends BankAccounts {
    private double creditLimit;
    private double charges;
    private static final String FILE_NAME = "credit_card_accounts.txt";

    /**
     * Default constructor
     */
    public CreditCardAccount() {
        super();
        this.creditLimit = 0;
        this.charges = 0;
    }

    /**
     * Constructor with account details
     * @param accountNo Account number
     * @param accountName Account holder's name
     * @param creditLimit Credit limit
     * @param charges Initial charges
     */
    public CreditCardAccount(int accountNo, String accountName, double creditLimit, double charges) {
        super(accountNo, accountName);
        this.creditLimit = creditLimit;
        this.charges = charges;
    }

    /**
     * Gets the credit limit
     * @return Credit limit
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * Gets the current charges
     * @return Current charges
     */
    public double getCharges() {
        return charges;
    }


    /**
     * Inquires available credit
     * @return Available credit
     */
    public double inquireAvailableCredit() {
        double availableCredit = creditLimit - charges;
        System.out.println("Available credit: ₱" + availableCredit);
        return availableCredit;
    }


    /**
     * Overrides deposit to prevent direct deposits
     */
    @Override
    public void deposit(double amount) {
        System.out.println("Direct deposit is not allowed for Credit Card Accounts. Please use payCard instead.");
    }

    /**
     * Overrides withdraw to prevent withdrawals
     */
    @Override
    public void withdraw(double amount) throws InvalidAmountException, AccountClosedException {
        throw new InvalidAmountException("Withdrawal is not allowed for Credit Card Accounts. Please use getCashAdvance instead.");
    }

    /**
     * Overrides inquireBalance to use inquireAvailableCredit instead
     */
    @Override
    public double inquireBalance() {
        System.out.println("Credit Card Accounts do not have a balance. Use inquireAvailableCredit instead.");
        return inquireAvailableCredit();
    }

    /**
     * Returns a string representation of the credit card account
     * @return String with account details
     */
    @Override
    public String toString() {
        return "Account Number: " + getAccountNo() +
                "\nAccount Name: " + getAccountName() +
                "\nAccount Type: Credit Card Account" +
                "\nCredit Limit: ₱" + creditLimit +
                "\nCurrent Charges: ₱" + charges +
                "\nAvailable Credit: ₱" + (creditLimit - charges) +
                "\nStatus: " + getStatus();
    }

    //FILE I/O OPERATION
    /**
     * Returns a string formatted for file storage
     * @return Formatted string with account data
     */
    public String toFileString() {
        return getAccountNo() + "," + getAccountName() + "," + getStatus() + "," +
                creditLimit + "," + charges;
    }

    /**
     * Parse account data from a string
     * @param data String containing account data
     * @return CreditCardAccount object
     */
    public static CreditCardAccount fromString(String data) {
        String[] fields = data.split(",");
        if (fields.length >= 5) {
            int accountNo = Integer.parseInt(fields[0]);
            String accountName = fields[1];
            String status = fields[2];
            double creditLimit = Double.parseDouble(fields[3]);
            double charges = Double.parseDouble(fields[4]);

            CreditCardAccount account = new CreditCardAccount(accountNo, accountName, creditLimit, charges);
            account.setStatus(status);
            return account;
        }
        return null;
    }

    /**
     * Logs a transaction for this credit card account
     * @param transactionType Type of transaction
     * @param amount Amount involved
     * @param description Description of transaction
     */
    public void logTransaction(String transactionType, double amount, String description) {
        FileManager.logTransaction(getAccountNo(), transactionType, amount, description);
    }





    //TRANSACTION LOGGING
    /**
     * Makes a payment to the credit card
     * @param amount Amount to pay
     * @throws InvalidAmountException If amount is invalid
     * @throws AccountClosedException If account is closed
     */
    public void payCard(double amount) throws InvalidAmountException, AccountClosedException {
        if (getStatus().equals("closed")) {
            throw new AccountClosedException("Transaction cancelled. Account is closed.");
        }

        if (amount <= 0) {
            throw new InvalidAmountException("Invalid amount. Please enter a positive value.");
        }

        if (amount > charges) {
            System.out.println("Payment amount exceeds current charges. Adjusting to pay only ₱" + charges);
            amount = charges;
        }

        charges -= amount;
        System.out.println("Payment of ₱" + amount + " made successfully.");

        // Log the transaction
        logTransaction("Payment", amount, "Card payment");
    }

    /**
     * Gets cash advance
     * @param amount Amount for cash advance
     * @throws InvalidAmountException If amount is invalid
     * @throws AccountClosedException If account is closed
     */
    public void getCashAdvance(double amount) throws InvalidAmountException, AccountClosedException, TransactionLimitException {
        if (getStatus().equals("closed")) {
            throw new AccountClosedException("Transaction cancelled. Account is closed.");
        }

        if (amount <= 0) {
            throw new InvalidAmountException("Invalid amount. Please enter a positive value.");
        }

        double availableCredit = inquireAvailableCredit();
        double maxAdvance = availableCredit * 0.5;

        if (amount > maxAdvance) {
            throw new TransactionLimitException("Transaction cancelled. Cash advance cannot exceed 50% of available credit (₱" + maxAdvance + ")");
        }

        charges += amount;
        System.out.println("Cash advance of ₱" + amount + " processed successfully.");

        // Log the transaction
        logTransaction("Cash Advance", amount, "Cash advance from credit card");
    }
}