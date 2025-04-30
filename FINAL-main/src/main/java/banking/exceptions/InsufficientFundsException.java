package src.main.java.banking.exceptions;

/**
 * Exception thrown when an account has insufficient funds for a transaction
 */
public class InsufficientFundsException extends Exception {
    
    public InsufficientFundsException() {
        super("Insufficient funds available for this transaction");
    }
    
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    public InsufficientFundsException(double amount, double balance) {
        super(String.format("Insufficient funds: Requested $%.2f but available balance is $%.2f", amount, balance));
    }
}
