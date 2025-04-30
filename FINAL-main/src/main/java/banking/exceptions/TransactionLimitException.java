package src.main.java.banking.exceptions;

/**
 * Exception thrown when a transaction exceeds account limits
 */
public class TransactionLimitException extends Exception {
    
    public TransactionLimitException() {
        super("Transaction exceeds the allowed limit for this account");
    }
    
    public TransactionLimitException(String message) {
        super(message);
    }
    
    public TransactionLimitException(double amount, double limit) {
        super(String.format("Transaction of $%.2f exceeds the limit of $%.2f", amount, limit));
    }
}
