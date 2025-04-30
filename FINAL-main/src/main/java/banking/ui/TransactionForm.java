package src.main.java.banking.ui;

import banking.exceptions.*;
import banking.models.Account;
import banking.services.AccountManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Form for performing transactions (deposit, withdraw, transfer)
 */
public class TransactionForm extends JFrame {
    private JPanel mainPanel;
    private JTextField accountField;
    private JTextField amountField;
    private JTextField destinationAccountField;
    private JButton processButton;
    private JButton cancelButton;
    private JLabel titleLabel;
    private JLabel accountLabel;
    private JLabel amountLabel;
    private JLabel destinationAccountLabel;
    private JLabel descriptionLabel;
    private JTextField descriptionField;
    
    private final String transactionType;
    private final AccountManager accountManager;
    
    /**
     * Creates a new transaction form
     *
     * @param transactionType the type of transaction (Deposit, Withdraw, Transfer)
     */
    public TransactionForm(String transactionType) {
        this.transactionType = transactionType;
        this.accountManager = AccountManager.getInstance();
        
        setupUI();
        setupActionListeners();
    }
    
    /**
     * Sets up the UI components
     */
    private void setupUI() {
        // Create components
        mainPanel = new JPanel(new GridBagLayout());
        titleLabel = new JLabel(transactionType + " Funds");
        accountLabel = new JLabel("Account Number:");
        amountLabel = new JLabel("Amount:");
        descriptionLabel = new JLabel("Description:");
        destinationAccountLabel = new JLabel("Destination Account:");
        
        accountField = new JTextField(20);
        amountField = new JTextField(20);
        descriptionField = new JTextField(20);
        destinationAccountField = new JTextField(20);
        
        processButton = new JButton("Process " + transactionType);
        cancelButton = new JButton("Cancel");
        
        // Set font for title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        mainPanel.add(accountLabel, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(accountField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(amountLabel, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(amountField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(descriptionLabel, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(descriptionField, gbc);
        
        // Only show destination account field for transfers
        if (transactionType.equals("Transfer")) {
            gbc.gridx = 0;
            gbc.gridy = 4;
            mainPanel.add(destinationAccountLabel, gbc);
            
            gbc.gridx = 1;
            mainPanel.add(destinationAccountField, gbc);
            
            gbc.gridy = 5;
        } else {
            gbc.gridy = 4;
        }
        
        gbc.gridx = 0;
        mainPanel.add(processButton, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(cancelButton, gbc);
        
        // Frame settings
        setContentPane(mainPanel);
        setTitle(transactionType + " Funds");
        setSize(450, transactionType.equals("Transfer") ? 300 : 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Sets up the action listeners for the buttons
     */
    private void setupActionListeners() {
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    processTransaction();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TransactionForm.this,
                            "Error: " + ex.getMessage(), "Transaction Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUImain();
            }
        });
    }
    
    /**
     * Processes the transaction based on the form data
     *
     * @throws Exception if the transaction fails
     */
    private void processTransaction() throws Exception {
        String accountNumber = accountField.getText().trim();
        String amountText = amountField.getText().trim();
        String description = descriptionField.getText().trim();
        
        if (accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
        
        if (amountText.isEmpty()) {
            throw new IllegalArgumentException("Amount is required");
        }
        
        if (description.isEmpty()) {
            description = transactionType + " transaction";
        }
        
        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a valid number");
        }
        
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        
        Account account = accountManager.getAccountByNumber(accountNumber);
        
        switch (transactionType) {
            case "Deposit":
                account.deposit(amount);
                JOptionPane.showMessageDialog(this,
                        String.format("Successfully deposited $%.2f", amount),
                        "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
                break;
                
            case "Withdraw":
                account.withdraw(amount);
                JOptionPane.showMessageDialog(this,
                        String.format("Successfully withdrew $%.2f", amount),
                        "Withdrawal Successful", JOptionPane.INFORMATION_MESSAGE);
                break;
                
            case "Transfer":
                String destinationAccountNumber = destinationAccountField.getText().trim();
                if (destinationAccountNumber.isEmpty()) {
                    throw new IllegalArgumentException("Destination account number is required");
                }
                
                Account destinationAccount = accountManager.getAccountByNumber(destinationAccountNumber);
                account.transfer(destinationAccount, amount);
                
                JOptionPane.showMessageDialog(this,
                        String.format("Successfully transferred $%.2f to account %s", 
                                amount, destinationAccountNumber),
                        "Transfer Successful", JOptionPane.INFORMATION_MESSAGE);
                break;
                
            default:
                throw new IllegalArgumentException("Unknown transaction type");
        }
        
        // Log transaction with description
        account.logTransaction(transactionType, amount, description);
        
        dispose();
        new GUImain();
    }
}
