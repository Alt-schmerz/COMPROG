package src.main.java.banking.interfaces;

/**
 * Interface for account verification operations
 */
public interface AccountVerifiable {
    /**
     * Verifies if account details are valid
     * @return true if account details are valid, false otherwise
     */
    boolean verifyAccountDetails();
}
