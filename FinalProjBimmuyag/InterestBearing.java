package FinalProjBimmuyag;

public interface InterestBearing {
    double calculateInterest();
    void applyInterest() throws InvalidAmountException, AccountClosedException;
    double getInterestRate();
    double applyMonthlyInterest();
}