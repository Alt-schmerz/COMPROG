package FinalProjBimmuyag;

public interface TransactionLoggable {
    void logTransaction(String transactionType, double amount, String description);
}
