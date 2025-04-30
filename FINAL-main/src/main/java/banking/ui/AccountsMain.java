package src.main.java.banking.ui;

import banking.services.AccountManager;
import banking.services.MonthlyInterestApplier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main GUI class for the banking application
 */
public class AccountsMain {
    private JButton createAccountButton;
    private JButton balanceInquiryButton;
    private JButton depositButton;
    private JButton accountInformationButton;
    private JButton withdrawButton;
    private JButton moneyTransferButton;
    private JButton closeAccountButton;
    private JButton exitButton;
    private JPanel mainPanel;
    
    private final AccountManager accountManager;
    private final MonthlyInterestApplier interestApplier;
    private JFrame frame;
    
    /**
     * Creates the main GUI
     */
    public AccountsMain() {
        this.accountManager = AccountManager.getInstance();
        this.interestApplier = new MonthlyInterestApplier();
        
        setupUI();
        setupActionListeners();
    }
    
    /**
     * Sets up the UI components
     */
    private void setupUI() {
        // Create the main frame
        frame = new JFrame("Banking System");
        
        // Create the main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 250, 255));
        
        // Create the components if they don't exist
        if (createAccountButton == null) createAccountButton = new JButton("Create Account");
        if (balanceInquiryButton == null) balanceInquiryButton = new JButton("Balance Inquiry");
        if (depositButton == null) depositButton = new JButton("Deposit");
        if (withdrawButton == null) withdrawButton = new JButton("Withdraw");
        if (moneyTransferButton == null) moneyTransferButton = new JButton("Money Transfer");
        if (accountInformationButton == null) accountInformationButton = new JButton("Account Information");
        if (closeAccountButton == null) closeAccountButton = new JButton("Close Account");
        if (exitButton == null) exitButton = new JButton("Exit");
        
        // Set up GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Add a title label
        JLabel titleLabel = new JLabel("SABOG FINANCE", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        
        // Add buttons to the left column
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(createAccountButton, gbc);
        
        gbc.gridy = 2;
        mainPanel.add(depositButton, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(moneyTransferButton, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(closeAccountButton, gbc);
        
        // Add buttons to the right column
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(balanceInquiryButton, gbc);
        
        gbc.gridy = 2;
        mainPanel.add(withdrawButton, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(accountInformationButton, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(exitButton, gbc);
        
        // Set the panel as the content pane
        frame.setContentPane(mainPanel);
        frame.setTitle("SABOG FINANCE");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Sets up the action listeners for the buttons
     */
    private void setupActionListeners() {
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AccountTypes();
            }
        });
        
        balanceInquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = JOptionPane.showInputDialog(frame, 
                        "Enter account number:", "Balance Inquiry", JOptionPane.QUESTION_MESSAGE);
                
                if (accountNumber != null && !accountNumber.trim().isEmpty()) {
                    try {
                        double balance = accountManager.getAccountByNumber(accountNumber).getBalance();
                        JOptionPane.showMessageDialog(frame,
                                String.format("Current balance: $%.2f", balance),
                                "Balance Inquiry", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, 
                                "Error: " + ex.getMessage(), "Balance Inquiry Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TransactionForm("Deposit");
            }
        });
        
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TransactionForm("Withdraw");
            }
        });
        
        moneyTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TransactionForm("Transfer");
            }
        });
        
        accountInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AccountInfoForm();
            }
        });
        
        closeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = JOptionPane.showInputDialog(frame, 
                        "Enter account number to close:", "Close Account", JOptionPane.QUESTION_MESSAGE);
                
                if (accountNumber != null && !accountNumber.trim().isEmpty()) {
                    try {
                        int confirm = JOptionPane.showConfirmDialog(frame,
                                "Are you sure you want to close this account?\nThis action cannot be undone.",
                                "Confirm Account Closure", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        
                        if (confirm == JOptionPane.YES_OPTION) {
                            accountManager.getAccountByNumber(accountNumber).closeAccount();
                            JOptionPane.showMessageDialog(frame,
                                    "Account closed successfully.", "Account Closed", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, 
                                "Error: " + ex.getMessage(), "Close Account Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to exit?", "Confirm Exit", 
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
    }
    
    /**
     * Shows the main GUI screen
     */
    public void show() {
        frame.setVisible(true);
    }
}
