package src.main.java.banking.exceptions;

/**
 * Exception thrown when an invalid account is accessed
 */
public class InvalidAccountException extends Exception {
    
    public InvalidAccountException() {
        super("Invalid account details or account does not exist");
    }
    
    public InvalidAccountException(String message) {
        super(message);
    }
    
    public InvalidAccountException(String accountNumber, String reason) {
        super(String.format("Invalid account: %s - %s", accountNumber, reason));
    }
}
