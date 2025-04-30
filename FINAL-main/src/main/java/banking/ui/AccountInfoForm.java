package src.main.java.banking.ui;

import banking.exceptions.InvalidAccountException;
import banking.models.Account;
import banking.services.AccountManager;
import banking.services.ReportGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Form for viewing account information and generating reports
 */
public class AccountInfoForm extends JFrame {
    private JPanel mainPanel;
    private JTextField accountField;
    private JTextArea detailsArea;
    private JButton viewDetailsButton;
    private JButton viewTransactionsButton;
    private JButton generateReportButton;
    private JButton backButton;
    private JPanel buttonPanel;
    private JLabel accountLabel;
    private JPanel reportPanel;
    private JButton searchAccountsButton;
    private JButton allActiveAccountsButton;
    private JButton allClosedAccountsButton;
    
    private final AccountManager accountManager;
    private final ReportGenerator reportGenerator;
    
    /**
     * Creates a new account information form
     */
    public AccountInfoForm() {
        this.accountManager = AccountManager.getInstance();
        this.reportGenerator = new ReportGenerator();
        
        setupUI();
        setupActionListeners();
    }
    
    /**
     * Sets up the UI components
     */
    private void setupUI() {
        // Create components
        mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel centerPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        reportPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        
        accountLabel = new JLabel("Account Number:");
        accountField = new JTextField(15);
        detailsArea = new JTextArea(15, 40);
        detailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        
        viewDetailsButton = new JButton("View Details");
        viewTransactionsButton = new JButton("View Transactions");
        generateReportButton = new JButton("Generate Report");
        backButton = new JButton("Back");
        searchAccountsButton = new JButton("Search Accounts");
        allActiveAccountsButton = new JButton("Active Accounts Report");
        allClosedAccountsButton = new JButton("Closed Accounts Report");
        
        // Layout
        topPanel.add(accountLabel);
        topPanel.add(accountField);
        topPanel.add(viewDetailsButton);
        
        buttonPanel.add(viewTransactionsButton);
        buttonPanel.add(generateReportButton);
        buttonPanel.add(searchAccountsButton);
        buttonPanel.add(backButton);
        
        reportPanel.add(allActiveAccountsButton);
        reportPanel.add(allClosedAccountsButton);
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(reportPanel, BorderLayout.SOUTH);
        
        // Frame settings
        setContentPane(mainPanel);
        setTitle("Account Information");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Sets up the action listeners for the buttons
     */
    private void setupActionListeners() {
        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String accountNumber = accountField.getText().trim();
                    if (accountNumber.isEmpty()) {
                        throw new IllegalArgumentException("Account number is required");
                    }
                    
                    Account account = accountManager.getAccountByNumber(accountNumber);
                    detailsArea.setText(account.getAccountDetails());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AccountInfoForm.this,
                            "Error: " + ex.getMessage(), "View Details Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        viewTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String accountNumber = accountField.getText().trim();
                    if (accountNumber.isEmpty()) {
                        throw new IllegalArgumentException("Account number is required");
                    }
                    
                    Account account = accountManager.getAccountByNumber(accountNumber);
                    detailsArea.setText(account.getTransactionHistory());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AccountInfoForm.this,
                            "Error: " + ex.getMessage(), "View Transactions Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String accountNumber = accountField.getText().trim();
                    if (accountNumber.isEmpty()) {
                        throw new IllegalArgumentException("Account number is required");
                    }
                    
                    // Ask for date range
                    String startDateStr = JOptionPane.showInputDialog(AccountInfoForm.this,
                            "Enter start date (YYYY-MM-DD) or leave blank for all transactions:",
                            "Date Range", JOptionPane.QUESTION_MESSAGE);
                    
                    String endDateStr = JOptionPane.showInputDialog(AccountInfoForm.this,
                            "Enter end date (YYYY-MM-DD) or leave blank for today:",
                            "Date Range", JOptionPane.QUESTION_MESSAGE);
                    
                    String reportPath;
                    if (startDateStr != null && !startDateStr.trim().isEmpty() && 
                            endDateStr != null && !endDateStr.trim().isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate startDate = LocalDate.parse(startDateStr.trim(), formatter);
                            LocalDate endDate = LocalDate.parse(endDateStr.trim(), formatter);
                            
                            reportPath = reportGenerator.generateTransactionReportByDateRange(
                                    accountNumber, startDate, endDate);
                        } catch (DateTimeParseException ex) {
                            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
                        }
                    } else {
                        reportPath = reportGenerator.generateAccountTransactionSummary(accountNumber);
                    }
                    
                    JOptionPane.showMessageDialog(AccountInfoForm.this,
                            "Report generated successfully: " + reportPath,
                            "Report Generated", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AccountInfoForm.this,
                            "Error: " + ex.getMessage(), "Generate Report Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUImain();
            }
        });
        
        searchAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AccountSearchForm();
            }
        });
        
        allActiveAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String reportPath = reportGenerator.generateActiveAccountsReport();
                    JOptionPane.showMessageDialog(AccountInfoForm.this,
                            "Active accounts report generated: " + reportPath,
                            "Report Generated", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(AccountInfoForm.this,
                            "Error generating report: " + ex.getMessage(),
                            "Report Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        allClosedAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String reportPath = reportGenerator.generateClosedAccountsReport();
                    JOptionPane.showMessageDialog(AccountInfoForm.this,
                            "Closed accounts report generated: " + reportPath,
                            "Report Generated", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(AccountInfoForm.this,
                            "Error generating report: " + ex.getMessage(),
                            "Report Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
