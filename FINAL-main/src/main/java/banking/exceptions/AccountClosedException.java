package src.main.java.banking.exceptions;

/**
 * Exception thrown when attempting operations on a closed account
 */
public class AccountClosedException extends Exception {
    
    public AccountClosedException() {
        super("This account has been closed and is no longer active");
    }
    
    public AccountClosedException(String message) {
        super(message);
    }
    
    public AccountClosedException(String accountNumber, String date) {
        super(String.format("Account %s was closed on %s and is no longer active", accountNumber, date));
    }
}
