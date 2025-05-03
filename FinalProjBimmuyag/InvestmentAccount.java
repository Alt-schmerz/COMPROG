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

public class InvestmentAccount extends BankAccounts implements InterestBearing {
    private final double minimumBalance;
    private final double interest; // example: 10% should be expressed as 0.10

    /**
     * Default constructor
     */
    public InvestmentAccount() {
        super();
        this.minimumBalance = 0;
        this.interest = 0;
    }

    /**
     * Constructor with account details
     * @param accountNo Account number
     * @param accountName Account holder's name
     * @param minimumBalance Minimum balance required
     * @param interest Interest rate (decimal form, e.g., 0.10 for 10%)
     */
    public InvestmentAccount(int accountNo, String accountName, double minimumBalance, double interest) {
        super(accountNo, accountName);
        this.minimumBalance = minimumBalance;
        this.interest = interest;
    }
    /**
     * Gets the interest rate
     * @return Interest rate
     */
    public double getInterestRate() {
        return interest;
    }

    /**
     * Gets the minimum balance required
     * @return Minimum balance
     */
    public double getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * Gets the interest rate
     * @return Interest rate
     */
    public double getInterest() {
        return interest;
    }

    /**
     * Adds investment to the account (overrides deposit)
     * @param amount Amount to invest
     */
    public void addInvestment(double amount) throws InvalidAmountException, AccountClosedException {
        if (getStatus().equals("closed")) {
            System.out.println("Transaction cancelled. Account is closed.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }

        double currentBalance = inquireBalance() + amount;
        if (currentBalance < minimumBalance) {
            System.out.println("Transaction cancelled. Amount doesn't meet minimum balance requirement of ₱" + minimumBalance);
            return;
        }

        super.deposit(amount);
        FileManager.logTransaction(getAccountNo(), "Investment Deposit", amount, "Investment added");
        System.out.println("Investment added successfully.");
    }

    /**
     * Inquires the current investment value with interest
     * @return Current investment value
     */
    public double inquireInvestmentValue() {
        double currentValue = inquireBalance() * (1 + interest);
        System.out.println("Current investment value: ₱" + currentValue);
        return currentValue;
    }

    /**
     * Overrides withdraw to prevent withdrawals
     */
    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawal is not allowed for Investment Accounts.");
    }

    /**
     * Closes the account and withdraws all funds with interest
     */
    @Override
    public void closeAccount() {
        if (getStatus().equals("closed")) {
            System.out.println("Account is already closed.");
            return;
        }

        double finalValue = inquireInvestmentValue();
        System.out.println("Withdrawing final value of ₱" + finalValue);
        FileManager.logTransaction(getAccountNo(), "Account Closed", finalValue, "Investment account closed with final value");
        setBalance(0);
        setStatus("closed");
        System.out.println("Investment Account closed successfully.");
    }

    /**
     * Calculates interest based on the current balance.
     * @return The calculated interest amount based on the current balance.
     */
    @Override
    public double calculateInterest() {
        return inquireBalance() * interest; // Interest = balance * interest rate
    }

    /**
     * Applies the calculated interest to the account balance.
     */
    @Override
    public void applyInterest() throws InvalidAmountException, AccountClosedException {
        double interestAmount = calculateInterest();
        deposit(interestAmount); // Use the deposit method to add interest to the balance
        FileManager.logTransaction(getAccountNo(), "Interest Applied", interestAmount, "Monthly interest");
    }

    /**
     * Returns a string representation of the investment account
     * @return String with account details
     */
    @Override
    public String toString() {
        return super.toString() +
                "\nAccount Type: Investment Account" +
                "\nMinimum Balance: ₱" + minimumBalance +
                "\nInterest Rate: " + (interest * 100) + "%" +
                "\nCurrent Investment Value: ₱" + inquireInvestmentValue();
    }
    /**
     * Applies monthly interest to the investment account
     * @return The amount of interest applied
     */
    public double applyMonthlyInterest() {
        double interestAmount = calculateInterest();
        setBalance(inquireBalance() + interestAmount);

        // Log the interest application
        logTransaction("Interest Applied", interestAmount, "Monthly interest");

        return interestAmount;
    }
}