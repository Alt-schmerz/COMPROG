import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simplified Banking Application without package dependencies
 */
public class SimpleBankingApp {
    // Main application components
    private JFrame mainFrame;
    private JPanel mainPanel;
    
    // Banking logic
    private Map<String, BankAccount> accounts = new HashMap<>();
    private int nextAccountNumber = 1000;
    
    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set look and feel: " + e.getMessage());
        }
        
        // Start the application
        SwingUtilities.invokeLater(() -> {
            SimpleBankingApp app = new SimpleBankingApp();
            app.createAndShowGUI();
        });
    }
    
    /**
     * Creates and displays the main GUI
     */
    private void createAndShowGUI() {
        // Create sample accounts
        createSampleAccounts();
        
        // Create main frame
        mainFrame = new JFrame("Banking Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700, 500);
        
        // Create main panel with a layout
        mainPanel = new JPanel(new BorderLayout());
        
        // Add header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Add main menu
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, BorderLayout.CENTER);
        
        // Set panel as content pane
        mainFrame.setContentPane(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    /**
     * Creates the header panel
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 141, 188));
        panel.setPreferredSize(new Dimension(700, 80));
        
        JLabel titleLabel = new JLabel("SABOG FINANCE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel);
        return panel;
    }
    
    /**
     * Creates the main menu panel
     */
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.ipadx = 50;
        gbc.ipady = 20;
        
        // Create buttons
        JButton createAccountBtn = createStyledButton("Create Account");
        JButton balanceInquiryBtn = createStyledButton("Balance Inquiry");
        JButton depositBtn = createStyledButton("Deposit");
        JButton withdrawBtn = createStyledButton("Withdraw");
        JButton transferBtn = createStyledButton("Transfer");
        JButton accountInfoBtn = createStyledButton("Account Information");
        JButton closeAccountBtn = createStyledButton("Close Account");
        JButton exitBtn = createStyledButton("Exit");
        
        // First row
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createAccountBtn, gbc);
        
        gbc.gridx = 1;
        panel.add(balanceInquiryBtn, gbc);
        
        // Second row
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(depositBtn, gbc);
        
        gbc.gridx = 1;
        panel.add(withdrawBtn, gbc);
        
        // Third row
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(transferBtn, gbc);
        
        gbc.gridx = 1;
        panel.add(accountInfoBtn, gbc);
        
        // Fourth row
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(closeAccountBtn, gbc);
        
        gbc.gridx = 1;
        panel.add(exitBtn, gbc);
        
        // Add button actions
        createAccountBtn.addActionListener(e -> showCreateAccountDialog());
        balanceInquiryBtn.addActionListener(e -> showBalanceInquiry());
        depositBtn.addActionListener(e -> showTransactionDialog("Deposit"));
        withdrawBtn.addActionListener(e -> showTransactionDialog("Withdraw"));
        transferBtn.addActionListener(e -> showTransferDialog());
        accountInfoBtn.addActionListener(e -> showAccountInformation());
        closeAccountBtn.addActionListener(e -> showCloseAccountDialog());
        exitBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(mainFrame, 
                "Are you sure you want to exit?", "Confirm Exit", 
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        return panel;
    }
    
    /**
     * Creates a styled button with consistent look
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(60, 141, 188));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }
    
    /**
     * Shows dialog to create a new account
     */
    private void showCreateAccountDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        JLabel nameLabel = new JLabel("Account Holder Name:");
        JTextField nameField = new JTextField(20);
        
        JLabel initialDepositLabel = new JLabel("Initial Deposit:");
        JTextField initialDepositField = new JTextField(20);
        
        JLabel accountTypeLabel = new JLabel("Account Type:");
        String[] accountTypes = {"Regular", "Checking", "Investment", "Credit Card"};
        JComboBox<String> accountTypeCombo = new JComboBox<>(accountTypes);
        
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(initialDepositLabel);
        panel.add(initialDepositField);
        panel.add(accountTypeLabel);
        panel.add(accountTypeCombo);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, 
                "Create New Account", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, 
                            "Please enter account holder name", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                double initialDeposit = 0;
                try {
                    initialDeposit = Double.parseDouble(initialDepositField.getText().trim());
                    if (initialDeposit < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(mainFrame, 
                            "Please enter a valid positive deposit amount", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String accountType = (String) accountTypeCombo.getSelectedItem();
                String accountNumber = createAccount(name, initialDeposit, accountType);
                
                JOptionPane.showMessageDialog(mainFrame, 
                        "Account created successfully!\nAccount Number: " + accountNumber, 
                        "Account Created", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "Error creating account: " + ex.getMessage(), 
                        "Account Creation Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Shows balance inquiry dialog
     */
    private void showBalanceInquiry() {
        String accountNumber = JOptionPane.showInputDialog(mainFrame, 
                "Enter account number:", "Balance Inquiry", JOptionPane.QUESTION_MESSAGE);
        
        if (accountNumber != null && !accountNumber.trim().isEmpty()) {
            try {
                BankAccount account = findAccount(accountNumber);
                JOptionPane.showMessageDialog(mainFrame,
                        String.format("Current balance: $%.2f", account.getBalance()),
                        "Balance Inquiry", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "Error: " + ex.getMessage(), 
                        "Balance Inquiry Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Shows transaction dialog (deposit or withdraw)
     */
    private void showTransactionDialog(String transactionType) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        
        JLabel accountLabel = new JLabel("Account Number:");
        JTextField accountField = new JTextField(20);
        
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);
        
        panel.add(accountLabel);
        panel.add(accountField);
        panel.add(amountLabel);
        panel.add(amountField);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, 
                transactionType + " Funds", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String accountNumber = accountField.getText().trim();
                if (accountNumber.isEmpty()) {
                    throw new Exception("Account number is required");
                }
                
                double amount = 0;
                try {
                    amount = Double.parseDouble(amountField.getText().trim());
                    if (amount <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Please enter a valid positive amount");
                }
                
                BankAccount account = findAccount(accountNumber);
                
                if (transactionType.equals("Deposit")) {
                    account.deposit(amount);
                    JOptionPane.showMessageDialog(mainFrame,
                            String.format("Successfully deposited $%.2f\nNew balance: $%.2f", 
                                    amount, account.getBalance()),
                            "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
                } else if (transactionType.equals("Withdraw")) {
                    account.withdraw(amount);
                    JOptionPane.showMessageDialog(mainFrame,
                            String.format("Successfully withdrew $%.2f\nNew balance: $%.2f", 
                                    amount, account.getBalance()),
                            "Withdrawal Successful", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "Error: " + ex.getMessage(), 
                        transactionType + " Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Shows transfer dialog
     */
    private void showTransferDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        JLabel sourceLabel = new JLabel("From Account Number:");
        JTextField sourceField = new JTextField(20);
        
        JLabel destLabel = new JLabel("To Account Number:");
        JTextField destField = new JTextField(20);
        
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);
        
        panel.add(sourceLabel);
        panel.add(sourceField);
        panel.add(destLabel);
        panel.add(destField);
        panel.add(amountLabel);
        panel.add(amountField);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, 
                "Transfer Funds", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String sourceAccount = sourceField.getText().trim();
                String destAccount = destField.getText().trim();
                
                if (sourceAccount.isEmpty() || destAccount.isEmpty()) {
                    throw new Exception("Both account numbers are required");
                }
                
                if (sourceAccount.equals(destAccount)) {
                    throw new Exception("Source and destination accounts cannot be the same");
                }
                
                double amount = 0;
                try {
                    amount = Double.parseDouble(amountField.getText().trim());
                    if (amount <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Please enter a valid positive amount");
                }
                
                BankAccount source = findAccount(sourceAccount);
                BankAccount destination = findAccount(destAccount);
                
                // Perform transfer
                source.withdraw(amount);
                destination.deposit(amount);
                
                JOptionPane.showMessageDialog(mainFrame,
                        String.format("Successfully transferred $%.2f from account %s to account %s", 
                                amount, sourceAccount, destAccount),
                        "Transfer Successful", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "Error: " + ex.getMessage(), 
                        "Transfer Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Shows account information dialog
     */
    private void showAccountInformation() {
        String accountNumber = JOptionPane.showInputDialog(mainFrame, 
                "Enter account number:", "Account Information", JOptionPane.QUESTION_MESSAGE);
        
        if (accountNumber != null && !accountNumber.trim().isEmpty()) {
            try {
                BankAccount account = findAccount(accountNumber);
                
                JTextArea textArea = new JTextArea(10, 30);
                textArea.setEditable(false);
                textArea.setText(account.getAccountDetails());
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                
                JOptionPane.showMessageDialog(mainFrame, scrollPane,
                        "Account Information", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "Error: " + ex.getMessage(), 
                        "Account Information Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Shows close account dialog
     */
    private void showCloseAccountDialog() {
        String accountNumber = JOptionPane.showInputDialog(mainFrame, 
                "Enter account number to close:", "Close Account", JOptionPane.QUESTION_MESSAGE);
        
        if (accountNumber != null && !accountNumber.trim().isEmpty()) {
            try {
                BankAccount account = findAccount(accountNumber);
                
                int confirm = JOptionPane.showConfirmDialog(mainFrame,
                        "Are you sure you want to close this account?\nThis action cannot be undone.",
                        "Confirm Account Closure", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    account.closeAccount();
                    JOptionPane.showMessageDialog(mainFrame,
                            "Account closed successfully.", 
                            "Account Closed", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, 
                        "Error: " + ex.getMessage(), 
                        "Close Account Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Creates a new account
     */
    private String createAccount(String name, double initialDeposit, String type) throws Exception {
        String accountNumber = generateAccountNumber();
        BankAccount account;
        
        switch (type) {
            case "Regular":
                account = new RegularBankAccount(accountNumber, name, initialDeposit);
                break;
            case "Checking":
                account = new CheckingAccount(accountNumber, name, initialDeposit);
                break;
            case "Investment":
                account = new InvestmentAccount(accountNumber, name, initialDeposit);
                break;
            case "Credit Card":
                account = new CreditCardAccount(accountNumber, name, initialDeposit);
                break;
            default:
                throw new Exception("Invalid account type");
        }
        
        accounts.put(accountNumber, account);
        return accountNumber;
    }
    
    /**
     * Finds an account by account number
     */
    private BankAccount findAccount(String accountNumber) throws Exception {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new Exception("Account not found: " + accountNumber);
        }
        return account;
    }
    
    /**
     * Generates a unique account number
     */
    private String generateAccountNumber() {
        return "ACC" + (nextAccountNumber++);
    }
    
    /**
     * Creates sample accounts for testing
     */
    private void createSampleAccounts() {
        try {
            createAccount("John Doe", 5000.0, "Regular");
            createAccount("Jane Smith", 2500.0, "Checking");
            createAccount("Robert Johnson", 10000.0, "Investment");
            createAccount("Alice Brown", 1000.0, "Credit Card");
            
            System.out.println("Sample accounts created successfully");
        } catch (Exception e) {
            System.err.println("Error creating sample accounts: " + e.getMessage());
        }
    }
    
    // Basic account classes
    
    /**
     * Base bank account class
     */
    private abstract class BankAccount {
        protected String accountNumber;
        protected String accountHolderName;
        protected double balance;
        protected boolean isActive;
        protected List<Transaction> transactions;
        
        public BankAccount(String accountNumber, String accountHolderName, double initialDeposit) throws Exception {
            if (initialDeposit < 0) {
                throw new Exception("Initial deposit cannot be negative");
            }
            
            this.accountNumber = accountNumber;
            this.accountHolderName = accountHolderName;
            this.balance = initialDeposit;
            this.isActive = true;
            this.transactions = new ArrayList<>();
            
            if (initialDeposit > 0) {
                logTransaction("Initial Deposit", initialDeposit);
            }
        }
        
        public String getAccountNumber() {
            return accountNumber;
        }
        
        public String getAccountHolderName() {
            return accountHolderName;
        }
        
        public double getBalance() {
            return balance;
        }
        
        public boolean isActive() {
            return isActive;
        }
        
        public void deposit(double amount) throws Exception {
            if (!isActive) {
                throw new Exception("Account is closed");
            }
            
            if (amount <= 0) {
                throw new Exception("Deposit amount must be positive");
            }
            
            balance += amount;
            logTransaction("Deposit", amount);
        }
        
        public void withdraw(double amount) throws Exception {
            if (!isActive) {
                throw new Exception("Account is closed");
            }
            
            if (amount <= 0) {
                throw new Exception("Withdrawal amount must be positive");
            }
            
            if (amount > balance) {
                throw new Exception("Insufficient funds");
            }
            
            balance -= amount;
            logTransaction("Withdrawal", amount);
        }
        
        public void closeAccount() throws Exception {
            if (!isActive) {
                throw new Exception("Account is already closed");
            }
            
            if (balance < 0) {
                throw new Exception("Cannot close account with negative balance");
            }
            
            isActive = false;
            logTransaction("Account Closed", 0);
        }
        
        protected void logTransaction(String type, double amount) {
            Transaction transaction = new Transaction(type, amount);
            transactions.add(transaction);
        }
        
        public abstract String getAccountType();
        
        public String getAccountDetails() {
            StringBuilder details = new StringBuilder();
            details.append("Account Number: ").append(accountNumber).append("\n");
            details.append("Account Holder: ").append(accountHolderName).append("\n");
            details.append("Account Type: ").append(getAccountType()).append("\n");
            details.append("Balance: $").append(String.format("%.2f", balance)).append("\n");
            details.append("Status: ").append(isActive ? "Active" : "Closed").append("\n\n");
            
            details.append("Transaction History:\n");
            if (transactions.isEmpty()) {
                details.append("No transactions found\n");
            } else {
                for (Transaction transaction : transactions) {
                    details.append(transaction).append("\n");
                }
            }
            
            return details.toString();
        }
    }
    
    /**
     * Regular bank account
     */
    private class RegularBankAccount extends BankAccount {
        public RegularBankAccount(String accountNumber, String accountHolderName, double initialDeposit) throws Exception {
            super(accountNumber, accountHolderName, initialDeposit);
        }
        
        @Override
        public String getAccountType() {
            return "Regular Account";
        }
    }
    
    /**
     * Checking account with transaction fees
     */
    private class CheckingAccount extends BankAccount {
        private static final double TRANSACTION_FEE = 1.5;
        private static final int FREE_TRANSACTIONS_PER_MONTH = 5;
        private int transactionsThisMonth;
        
        public CheckingAccount(String accountNumber, String accountHolderName, double initialDeposit) throws Exception {
            super(accountNumber, accountHolderName, initialDeposit);
            this.transactionsThisMonth = 0;
        }
        
        @Override
        public void deposit(double amount) throws Exception {
            super.deposit(amount);
            applyTransactionFee();
        }
        
        @Override
        public void withdraw(double amount) throws Exception {
            super.withdraw(amount);
            applyTransactionFee();
        }
        
        private void applyTransactionFee() throws Exception {
            transactionsThisMonth++;
            
            if (transactionsThisMonth > FREE_TRANSACTIONS_PER_MONTH) {
                if (balance < TRANSACTION_FEE) {
                    throw new Exception("Insufficient funds to cover transaction fee");
                }
                
                balance -= TRANSACTION_FEE;
                logTransaction("Transaction Fee", TRANSACTION_FEE);
            }
        }
        
        @Override
        public String getAccountType() {
            return "Checking Account";
        }
    }
    
    /**
     * Investment account with interest
     */
    private class InvestmentAccount extends BankAccount {
        private static final double INTEREST_RATE = 0.025; // 2.5%
        
        public InvestmentAccount(String accountNumber, String accountHolderName, double initialDeposit) throws Exception {
            super(accountNumber, accountHolderName, initialDeposit);
        }
        
        public void applyInterest() {
            double interestAmount = balance * INTEREST_RATE / 12; // Monthly interest
            balance += interestAmount;
            logTransaction("Interest", interestAmount);
        }
        
        @Override
        public String getAccountType() {
            return "Investment Account";
        }
    }
    
    /**
     * Credit card account
     */
    private class CreditCardAccount extends BankAccount {
        private final double creditLimit;
        
        public CreditCardAccount(String accountNumber, String accountHolderName, double creditLimit) throws Exception {
            super(accountNumber, accountHolderName, 0); // Start with 0 balance
            this.creditLimit = creditLimit;
        }
        
        @Override
        public void withdraw(double amount) throws Exception {
            if (!isActive) {
                throw new Exception("Account is closed");
            }
            
            if (amount <= 0) {
                throw new Exception("Purchase amount must be positive");
            }
            
            if (amount > getAvailableCredit()) {
                throw new Exception("Exceeds available credit");
            }
            
            balance -= amount;
            logTransaction("Purchase", amount);
        }
        
        public double getAvailableCredit() {
            return creditLimit + balance; // Balance is negative for credit card debt
        }
        
        @Override
        public String getAccountType() {
            return "Credit Card Account";
        }
        
        @Override
        public String getAccountDetails() {
            StringBuilder details = new StringBuilder(super.getAccountDetails());
            details.append("\nCredit Limit: $").append(String.format("%.2f", creditLimit));
            details.append("\nAvailable Credit: $").append(String.format("%.2f", getAvailableCredit()));
            return details.toString();
        }
    }
    
    /**
     * Transaction class
     */
    private class Transaction {
        private final String type;
        private final double amount;
        private final long timestamp;
        
        public Transaction(String type, double amount) {
            this.type = type;
            this.amount = amount;
            this.timestamp = System.currentTimeMillis();
        }
        
        @Override
        public String toString() {
            return String.format("[%s] %s: $%.2f", 
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp)),
                    type, amount);
        }
    }
}