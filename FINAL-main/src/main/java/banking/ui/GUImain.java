
package src.main.java.banking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUImain extends JFrame implements ActionListener {
    private JButton createAccountButton;
    private JButton balanceInquiryButton;
    private JButton depositButton;
    private JButton accountInformationButton;
    private JButton withdrawButton;
    private JButton moneyTransferButton;
    private JButton closeAccountButton;
    private JButton exitButton;
    private JPanel mainPanel;
    
    public GUImain() {
        setupUI();
        setupActionListeners();
    }
    
    private void setupUI() {
        // Set fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        mainPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Initialize buttons with larger font
        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        
        createAccountButton = createStyledButton("Create Account", buttonFont);
        balanceInquiryButton = createStyledButton("Balance Inquiry", buttonFont);
        depositButton = createStyledButton("Deposit", buttonFont);
        withdrawButton = createStyledButton("Withdraw", buttonFont);
        moneyTransferButton = createStyledButton("Money Transfer", buttonFont);
        accountInformationButton = createStyledButton("Account Information", buttonFont);
        closeAccountButton = createStyledButton("Close Account", buttonFont);
        exitButton = createStyledButton("Exit", buttonFont);
        
        // Add components to panel
        mainPanel.add(createAccountButton);
        mainPanel.add(balanceInquiryButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawButton);
        mainPanel.add(moneyTransferButton);
        mainPanel.add(accountInformationButton);
        mainPanel.add(closeAccountButton);
        mainPanel.add(exitButton);
        
        setBackground(Color.CYAN);
        setContentPane(mainPanel);
        setTitle("SABOG FINANCE");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(new Color(51, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }
    
    private void setupActionListeners() {
        createAccountButton.addActionListener(e -> {
            dispose();
            new AccountTypes();
        });
        
        balanceInquiryButton.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog(this, 
                "Enter account number:", "Balance Inquiry", JOptionPane.QUESTION_MESSAGE);
            
            if (accountNumber != null && !accountNumber.trim().isEmpty()) {
                try {
                    double balance = banking.services.AccountManager.getInstance()
                        .getAccountByNumber(accountNumber).getBalance();
                    JOptionPane.showMessageDialog(this,
                        String.format("Current balance: $%.2f", balance),
                        "Balance Inquiry", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: " + ex.getMessage(), "Balance Inquiry Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        depositButton.addActionListener(e -> {
            dispose();
            new TransactionForm("Deposit");
        });
        
        withdrawButton.addActionListener(e -> {
            dispose();
            new TransactionForm("Withdraw");
        });
        
        moneyTransferButton.addActionListener(e -> {
            dispose();
            new TransactionForm("Transfer");
        });
        
        accountInformationButton.addActionListener(e -> {
            dispose();
            new AccountInfoForm();
        });
        
        closeAccountButton.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog(this, 
                "Enter account number to close:", "Close Account", JOptionPane.QUESTION_MESSAGE);
            
            if (accountNumber != null && !accountNumber.trim().isEmpty()) {
                try {
                    int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to close this account?\nThis action cannot be undone.",
                        "Confirm Account Closure", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        banking.services.AccountManager.getInstance()
                            .getAccountByNumber(accountNumber).closeAccount();
                        JOptionPane.showMessageDialog(this,
                            "Account closed successfully.", "Account Closed", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: " + ex.getMessage(), "Close Account Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        exitButton.addActionListener(e -> System.exit(0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handled by lambda expressions
    }
}
