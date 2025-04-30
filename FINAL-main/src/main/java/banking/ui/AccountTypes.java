package src.main.java.banking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for selecting account types
 */
public class AccountTypes implements ActionListener {
    private JPanel Types;
    private JButton bankAccountsButton;
    private JButton investmentAccountsButton;
    private JButton checkingAccountsButton;
    private JButton creditCardAccountsButton;
    private JButton backButton;
    JFrame typeFrame = new JFrame();
    
    /**
     * Creates a new account types selection screen
     */
    public AccountTypes() {
        // Create the panel
        Types = new JPanel(new GridLayout(4, 2, 10, 10));
        
        // Create the components if they don't exist
        if (bankAccountsButton == null) bankAccountsButton = new JButton("Bank Accounts");
        if (checkingAccountsButton == null) checkingAccountsButton = new JButton("Checking Accounts");
        if (investmentAccountsButton == null) investmentAccountsButton = new JButton("Investment Accounts");
        if (creditCardAccountsButton == null) creditCardAccountsButton = new JButton("Credit Card Accounts");
        if (backButton == null) backButton = new JButton("Back");
        
        // Add a title label
        JLabel titleLabel = new JLabel("Account Types", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Add components to the panel
        Types.add(titleLabel);
        Types.add(new JLabel()); // Empty cell for grid alignment
        Types.add(bankAccountsButton);
        Types.add(investmentAccountsButton);
        Types.add(checkingAccountsButton);
        Types.add(creditCardAccountsButton);
        Types.add(backButton);
        
        // Set up the frame
        typeFrame.setContentPane(Types);
        typeFrame.setTitle("SABOG FINANCE");
        typeFrame.setSize(500, 500);
        typeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        typeFrame.setLocationRelativeTo(null);
        typeFrame.setVisible(true);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeFrame.dispose();
                new AccountsMain(); // Changed from GUImain to AccountsMain
            }
        });
        
        bankAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeFrame.dispose();
                new AccountCreationForm("Bank Account");
            }
        });
        
        checkingAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeFrame.dispose();
                new AccountCreationForm("Checking Account");
            }
        });
        
        investmentAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeFrame.dispose();
                new AccountCreationForm("Investment Account");
            }
        });
        
        creditCardAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeFrame.dispose();
                new AccountCreationForm("Credit Card Account");
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handled by anonymous inner classes
    }
}
