package src.main.java.banking;

import banking.services.AccountManager;
import banking.services.MonthlyInterestApplier;
import banking.ui.AccountsMain;
import banking.models.*;
import banking.exceptions.*;

import javax.swing.*;
import java.awt.EventQueue;

/**
 * Main entry point for the banking application
 */
public class MainApplication {
    public static void main(String[] args) {
        // Enable anti-aliased text
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Use system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
        
        // Create some example accounts for testing
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createSampleAccounts();
                
                // Start monthly interest applier
                MonthlyInterestApplier interestApplier = new MonthlyInterestApplier();
                interestApplier.startMonthlyInterestApplication();
                
                // Launch main application window
                new AccountsMain();
            }
        });
    }
    
    /**
     * Creates sample accounts for testing purposes
     */
    private static void createSampleAccounts() {
        AccountManager accountManager = AccountManager.getInstance();
        
        try {
            // Create sample bank account
            BankAccount bankAccount = new BankAccount("John Doe", 5000.00);
            accountManager.addAccount(bankAccount);
            
            // Create sample checking account
            CheckingAccount checkingAccount = new CheckingAccount("Jane Smith", 2500.00);
            accountManager.addAccount(checkingAccount);
            
            // Create sample investment account
            InvestmentAccount investmentAccount = new InvestmentAccount("Robert Johnson", 10000.00);
            accountManager.addAccount(investmentAccount);
            
            // Create sample credit card account
            CreditCardAccount creditCardAccount = new CreditCardAccount("Alice Brown", 5000.00);
            accountManager.addAccount(creditCardAccount);
            
            // Perform some sample transactions
            bankAccount.deposit(1000.00);
            bankAccount.withdraw(500.00);
            
            checkingAccount.deposit(500.00);
            
            investmentAccount.deposit(2000.00);
            investmentAccount.applyInterest();
            
            creditCardAccount.withdraw(1000.00); // Make a purchase
            creditCardAccount.deposit(200.00);   // Make a payment
            
            System.out.println("Sample accounts created successfully");
            
        } catch (Exception e) {
            System.err.println("Error creating sample accounts: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
