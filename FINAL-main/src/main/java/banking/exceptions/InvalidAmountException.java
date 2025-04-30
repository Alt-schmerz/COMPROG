package src.main.java.banking.exceptions;

/**
 * Exception thrown when an invalid amount is used in a transaction
 */
public class InvalidAmountException extends Exception {
    
    public InvalidAmountException() {
        super("Invalid transaction amount");
    }
    
    public InvalidAmountException(String message) {
        super(message);
    }
    
    public InvalidAmountException(double amount) {
        super(String.format("Invalid amount: $%.2f. Amount must be positive", amount));
    }
}
