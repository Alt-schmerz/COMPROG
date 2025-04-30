package src.main.java.banking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main GUI class for the banking application
 */
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
    
    /**
     * Creates a new main GUI
     */
    public GUImain() {
        setBackground(Color.CYAN);
        setContentPane(mainPanel);
        setTitle("SABOG FINANCE");
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountTypes accType = new AccountTypes();
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TransactionForm("Deposit");
            }
        });
        
        moneyTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TransactionForm("Transfer");
            }
        });
        
        balanceInquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = JOptionPane.showInputDialog(GUImain.this, 
                        "Enter account number:", "Balance Inquiry", JOptionPane.QUESTION_MESSAGE);
                
                if (accountNumber != null && !accountNumber.trim().isEmpty()) {
                    try {
                        double balance = banking.services.AccountManager.getInstance()
                                .getAccountByNumber(accountNumber).getBalance();
                        JOptionPane.showMessageDialog(GUImain.this,
                                String.format("Current balance: $%.2f", balance),
                                "Balance Inquiry", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GUImain.this, 
                                "Error: " + ex.getMessage(), "Balance Inquiry Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TransactionForm("Withdraw");
            }
        });
        
        accountInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AccountInfoForm();
            }
        });
        
        closeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = JOptionPane.showInputDialog(GUImain.this, 
                        "Enter account number to close:", "Close Account", JOptionPane.QUESTION_MESSAGE);
                
                if (accountNumber != null && !accountNumber.trim().isEmpty()) {
                    try {
                        int confirm = JOptionPane.showConfirmDialog(GUImain.this,
                                "Are you sure you want to close this account?\nThis action cannot be undone.",
                                "Confirm Account Closure", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        
                        if (confirm == JOptionPane.YES_OPTION) {
                            banking.services.AccountManager.getInstance()
                                    .getAccountByNumber(accountNumber).closeAccount();
                            JOptionPane.showMessageDialog(GUImain.this,
                                    "Account closed successfully.", "Account Closed", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GUImain.this, 
                                "Error: " + ex.getMessage(), "Close Account Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handled by anonymous inner classes
    }
}
