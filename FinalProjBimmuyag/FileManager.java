package FinalProjBimmuyag;

import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Handles File I/O operations for the banking application
 */
public class FileManager {
    private static final String ACCOUNTS_DIR = "accounts/";
    private static final String TRANSACTIONS_DIR = "transactions/";
    private static final String REPORTS_DIR = "reports/";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Creates required directories for file storage
     */
    public static void initializeDirectories() {
        createDirectory(ACCOUNTS_DIR);
        createDirectory(TRANSACTIONS_DIR);
        createDirectory(REPORTS_DIR);
    }

    // Add to FileManager.java
    public static ArrayList<BankAccounts> searchAccountsByName(
            ArrayList<BankAccounts> bankAccounts,
            ArrayList<InvestmentAccount> investmentAccounts,
            ArrayList<CheckingAccount> checkingAccounts,
            ArrayList<CreditCardAccount> creditCardAccounts,
            String searchName) {

        ArrayList<BankAccounts> results = new ArrayList<>();

        // Search all account types
        for (BankAccounts account : bankAccounts) {
            if (account.getAccountName().toLowerCase().contains(searchName.toLowerCase())) {
                results.add(account);
            }
        }

        for (InvestmentAccount account : investmentAccounts) {
            if (account.getAccountName().toLowerCase().contains(searchName.toLowerCase())) {
                results.add(account);
            }
        }

        for (CheckingAccount account : checkingAccounts) {
            if (account.getAccountName().toLowerCase().contains(searchName.toLowerCase())) {
                results.add(account);
            }
        }

        for (CreditCardAccount account : creditCardAccounts) {
            if (account.getAccountName().toLowerCase().contains(searchName.toLowerCase())) {
                results.add(account);
            }
        }

        return results;
    }

    public static BankAccounts searchAccountByNumber(
            ArrayList<BankAccounts> bankAccounts,
            ArrayList<InvestmentAccount> investmentAccounts,
            ArrayList<CheckingAccount> checkingAccounts,
            ArrayList<CreditCardAccount> creditCardAccounts,
            int accountNumber) {

        // Search all account types
        for (BankAccounts account : bankAccounts) {
            if (account.getAccountNo() == accountNumber) {
                return account;
            }
        }

        for (InvestmentAccount account : investmentAccounts) {
            if (account.getAccountNo() == accountNumber) {
                return account;
            }
        }

        for (CheckingAccount account : checkingAccounts) {
            if (account.getAccountNo() == accountNumber) {
                return account;
            }
        }

        for (CreditCardAccount account : creditCardAccounts) {
            if (account.getAccountNo() == accountNumber) {
                return account;
            }
        }

        return null;
    }


    /**
     * Creates a directory if it doesn't exist
     * @param directoryPath Path of the directory to create
     */
    private static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Saves all accounts to files
     * @param bankAccounts List of regular bank accounts
     * @param investmentAccounts List of investment accounts
     * @param checkingAccounts List of checking accounts
     * @param creditCardAccounts List of credit card accounts
     */
    public static void saveAllAccounts(
            ArrayList<BankAccounts> bankAccounts,
            ArrayList<InvestmentAccount> investmentAccounts,
            ArrayList<CheckingAccount> checkingAccounts,
            ArrayList<CreditCardAccount> creditCardAccounts) {

        // Assuming you have a PrintWriter declared somewhere
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("accounts.txt"));

            saveRegularAccounts(bankAccounts);
            saveInvestmentAccounts(investmentAccounts);
            saveCheckingAccounts(checkingAccounts);
            saveCreditCardAccounts(creditCardAccounts);

            // Add code to save reopenDate for each account type
            // Need to iterate through each account list
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // For bank accounts
            for (BankAccounts account : bankAccounts) {
                writer.println(account.getReopenDate() != null ? dateFormat.format(account.getReopenDate()) : "null");
            }

            // For investment accounts
            for (InvestmentAccount account : investmentAccounts) {
                writer.println(account.getReopenDate() != null ? dateFormat.format(account.getReopenDate()) : "null");
            }

            // For checking accounts
            for (CheckingAccount account : checkingAccounts) {
                writer.println(account.getReopenDate() != null ? dateFormat.format(account.getReopenDate()) : "null");
            }

            // For credit card accounts
            for (CreditCardAccount account : creditCardAccounts) {
                writer.println(account.getReopenDate() != null ? dateFormat.format(account.getReopenDate()) : "null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Saves regular bank accounts to file
     * @param accounts List of regular bank accounts
     */
    private static void saveRegularAccounts(ArrayList<BankAccounts> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_DIR + "bank_accounts.csv"))) {
            writer.println("AccountNo,AccountName,Balance,Status");
            for (BankAccounts account : accounts) {
                writer.println(account.getAccountNo() + "," +
                        account.getAccountName() + "," +
                        account.inquireBalance() + "," +
                        account.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Error saving bank accounts: " + e.getMessage());
        }
    }

    /**
     * Saves investment accounts to file
     * @param accounts List of investment accounts
     */
    private static void saveInvestmentAccounts(ArrayList<InvestmentAccount> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_DIR + "investment_accounts.csv"))) {
            writer.println("AccountNo,AccountName,Balance,MinimumBalance,InterestRate,Status");
            for (InvestmentAccount account : accounts) {
                writer.println(account.getAccountNo() + "," +
                        account.getAccountName() + "," +
                        account.inquireBalance() + "," +
                        account.getMinimumBalance() + "," +
                        account.getInterestRate() + "," +
                        account.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Error saving investment accounts: " + e.getMessage());
        }
    }

    /**
     * Saves checking accounts to file
     * @param accounts List of checking accounts
     */
    private static void saveCheckingAccounts(ArrayList<CheckingAccount> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_DIR + "checking_accounts.csv"))) {
            writer.println("AccountNo,AccountName,Balance,MinimumBalance,Status");
            for (CheckingAccount account : accounts) {
                writer.println(account.getAccountNo() + "," +
                        account.getAccountName() + "," +
                        account.inquireBalance() + "," +
                        account.getMinimumBalance() + "," +
                        account.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Error saving checking accounts: " + e.getMessage());
        }
    }

    /**
     * Saves credit card accounts to file
     * @param accounts List of credit card accounts
     */
    private static void saveCreditCardAccounts(ArrayList<CreditCardAccount> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_DIR + "credit_card_accounts.csv"))) {
            writer.println("AccountNo,AccountName,CreditLimit,Charges,Status");
            for (CreditCardAccount account : accounts) {
                writer.println(account.getAccountNo() + "," +
                        account.getAccountName() + "," +
                        account.getCreditLimit() + "," +
                        account.getCharges() + "," +
                        account.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Error saving credit card accounts: " + e.getMessage());
        }
    }

    /**
     * Loads all accounts from files
     * @param bankAccounts List to store loaded regular bank accounts
     * @param investmentAccounts List to store loaded investment accounts
     * @param checkingAccounts List to store loaded checking accounts
     * @param creditCardAccounts List to store loaded credit card accounts
     */
    public static void loadAllAccounts(
            ArrayList<BankAccounts> bankAccounts,
            ArrayList<InvestmentAccount> investmentAccounts,
            ArrayList<CheckingAccount> checkingAccounts,
            ArrayList<CreditCardAccount> creditCardAccounts) {

        // Assuming you have a BufferedReader declared somewhere
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("accounts.txt"));

            loadRegularAccounts(bankAccounts);
            loadInvestmentAccounts(investmentAccounts);
            loadCheckingAccounts(checkingAccounts);
            loadCreditCardAccounts(creditCardAccounts);

            // Add code to load reopenDate for each account type
            // Need to iterate through all account types

            // For bank accounts
            for (BankAccounts account : bankAccounts) {
                String reopenDateStr = reader.readLine();
                if (reopenDateStr != null && !reopenDateStr.equals("null")) {
                    account.reopenAccount();
                    // You might need to set the reopenDate directly if it's not automatically set in reopenAccount()
                    // account.setReopenDate(new SimpleDateFormat("yyyy-MM-dd").parse(reopenDateStr));
                }
            }

            // For investment accounts
            for (InvestmentAccount account : investmentAccounts) {
                String reopenDateStr = reader.readLine();
                if (reopenDateStr != null && !reopenDateStr.equals("null")) {
                    account.reopenAccount();
                    // You might need to set the reopenDate directly if it's not automatically set in reopenAccount()
                }
            }

            // For checking accounts
            for (CheckingAccount account : checkingAccounts) {
                String reopenDateStr = reader.readLine();
                if (reopenDateStr != null && !reopenDateStr.equals("null")) {
                    account.reopenAccount();
                    // You might need to set the reopenDate directly if it's not automatically set in reopenAccount()
                }
            }

            // For credit card accounts
            for (CreditCardAccount account : creditCardAccounts) {
                String reopenDateStr = reader.readLine();
                if (reopenDateStr != null && !reopenDateStr.equals("null")) {
                    account.reopenAccount();
                    // You might need to set the reopenDate directly if it's not automatically set in reopenAccount()
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads regular bank accounts from file
     * @param accounts List to store loaded regular bank accounts
     */
    private static void loadRegularAccounts(ArrayList<BankAccounts> accounts) {
        File file = new File(ACCOUNTS_DIR + "bank_accounts.csv");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    int accountNo = Integer.parseInt(data[0]);
                    String accountName = data[1];
                    double balance = Double.parseDouble(data[2]);
                    String status = data[3];

                    BankAccounts account = new BankAccounts(accountNo, accountName);
                    account.setBalance(balance);
                    if (status.equals("closed")) {
                        account.closeAccount();
                    }
                    accounts.add(account);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading bank accounts: " + e.getMessage());
        }
    }

    /**
     * Loads investment accounts from file
     * @param accounts List to store loaded investment accounts
     */
    private static void loadInvestmentAccounts(ArrayList<InvestmentAccount> accounts) {
        File file = new File(ACCOUNTS_DIR + "investment_accounts.csv");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    int accountNo = Integer.parseInt(data[0]);
                    String accountName = data[1];
                    double balance = Double.parseDouble(data[2]);
                    double minimumBalance = Double.parseDouble(data[3]);
                    double interestRate = Double.parseDouble(data[4]);
                    String status = data[5];

                    InvestmentAccount account = new InvestmentAccount(accountNo, accountName, minimumBalance, interestRate);
                    account.setBalance(balance);
                    if (status.equals("closed")) {
                        account.closeAccount();
                    }
                    accounts.add(account);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading investment accounts: " + e.getMessage());
        }
    }

    /**
     * Loads checking accounts from file
     * @param accounts List to store loaded checking accounts
     */
    private static void loadCheckingAccounts(ArrayList<CheckingAccount> accounts) {
        File file = new File(ACCOUNTS_DIR + "checking_accounts.csv");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    int accountNo = Integer.parseInt(data[0]);
                    String accountName = data[1];
                    double balance = Double.parseDouble(data[2]);
                    double minimumBalance = Double.parseDouble(data[3]);
                    String status = data[4];

                    CheckingAccount account = new CheckingAccount(accountNo, accountName, minimumBalance);
                    account.setBalance(balance);
                    if (status.equals("closed")) {
                        account.closeAccount();
                    }
                    accounts.add(account);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading checking accounts: " + e.getMessage());
        }
    }

    /**
     * Loads credit card accounts from file
     * @param accounts List to store loaded credit card accounts
     */
    private static void loadCreditCardAccounts(ArrayList<CreditCardAccount> accounts) {
        File file = new File(ACCOUNTS_DIR + "credit_card_accounts.csv");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    int accountNo = Integer.parseInt(data[0]);
                    String accountName = data[1];
                    double creditLimit = Double.parseDouble(data[2]);
                    double charges = Double.parseDouble(data[3]);
                    String status = data[4];

                    CreditCardAccount account = new CreditCardAccount(accountNo, accountName, creditLimit, charges);
                    if (status.equals("closed")) {
                        account.closeAccount();
                    }
                    accounts.add(account);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading credit card accounts: " + e.getMessage());
        }
    }

    /**
     * Logs a transaction for an account
     * @param accountNo Account number
     * @param transactionType Type of transaction
     * @param amount Transaction amount
     * @param description Transaction description
     */
    public static void logTransaction(int accountNo, String transactionType, double amount, String description) {
        String filename = TRANSACTIONS_DIR + accountNo + "_transactions.csv";
        boolean fileExists = new File(filename).exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Write header if file doesn't exist
            if (!fileExists) {
                writer.println("Timestamp,TransactionType,Amount,Description");
            }

            LocalDateTime now = LocalDateTime.now();
            writer.println(now.format(DATE_FORMAT) + "," +
                    transactionType + "," +
                    amount + "," +
                    description);
        } catch (IOException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }

    /**
     * Generates a report of all active accounts
     */
    public static void generateActiveAccountsReport() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(REPORTS_DIR + "active_accounts_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt"))) {
            writer.println("================= ACTIVE ACCOUNTS REPORT =================");
            writer.println("Generated on: " + LocalDateTime.now().format(DATE_FORMAT));
            writer.println("=========================================================\n");

            writeAccountsToReport(writer, "bank_accounts.csv", "Regular Bank Accounts", "active");
            writeAccountsToReport(writer, "investment_accounts.csv", "Investment Accounts", "active");
            writeAccountsToReport(writer, "checking_accounts.csv", "Checking Accounts", "active");
            writeAccountsToReport(writer, "credit_card_accounts.csv", "Credit Card Accounts", "active");

            writer.println("\n==================== END OF REPORT ====================");
            System.out.println("Active accounts report generated successfully!");
        } catch (IOException e) {
            System.out.println("Error generating active accounts report: " + e.getMessage());
        }
    }

    /**
     * Generates a report of all closed accounts
     */
    public static void generateClosedAccountsReport() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(REPORTS_DIR + "closed_accounts_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt"))) {
            writer.println("================= CLOSED ACCOUNTS REPORT =================");
            writer.println("Generated on: " + LocalDateTime.now().format(DATE_FORMAT));
            writer.println("==========================================================\n");

            writeAccountsToReport(writer, "bank_accounts.csv", "Regular Bank Accounts", "closed");
            writeAccountsToReport(writer, "investment_accounts.csv", "Investment Accounts", "closed");
            writeAccountsToReport(writer, "checking_accounts.csv", "Checking Accounts", "closed");
            writeAccountsToReport(writer, "credit_card_accounts.csv", "Credit Card Accounts", "closed");

            writer.println("\n==================== END OF REPORT ====================");
            System.out.println("Closed accounts report generated successfully!");
        } catch (IOException e) {
            System.out.println("Error generating closed accounts report: " + e.getMessage());
        }
    }

    /**
     * Generates a transaction summary report for a specific account
     * @param accountNo Account number to generate report for
     */
    public static void generateTransactionSummary(int accountNo) {
        File transactionFile = new File(TRANSACTIONS_DIR + accountNo + "_transactions.csv");
        if (!transactionFile.exists()) {
            System.out.println("No transaction history found for account #" + accountNo);
            return;
        }

        try (
                BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
                PrintWriter writer = new PrintWriter(new FileWriter(REPORTS_DIR + "transaction_summary_" + accountNo + "_" +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt"))
        ) {
            writer.println("================= TRANSACTION SUMMARY =================");
            writer.println("Account #: " + accountNo);
            writer.println("Generated on: " + LocalDateTime.now().format(DATE_FORMAT));
            writer.println("======================================================\n");

            // Skip header
            String header = reader.readLine();
            writer.println(String.format("%-25s %-15s %-10s %s", "Timestamp", "Type", "Amount", "Description"));
            writer.println("------------------------------------------------------");

            String line;
            int transactionCount = 0;
            double totalDeposits = 0;
            double totalWithdrawals = 0;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String timestamp = data[0];
                    String type = data[1];
                    double amount = Double.parseDouble(data[2]);
                    String description = data[3];

                    writer.println(String.format("%-25s %-15s ₱%-10.2f %s", timestamp, type, amount, description));

                    transactionCount++;
                    if (type.contains("Deposit") || type.contains("Transfer In")) {
                        totalDeposits += amount;
                    } else if (type.contains("Withdrawal") || type.contains("Transfer Out")) {
                        totalWithdrawals += amount;
                    }
                }
            }

            writer.println("\n------------------ SUMMARY ------------------");
            writer.println("Total transactions: " + transactionCount);
            writer.println(String.format("Total deposits: ₱%.2f", totalDeposits));
            writer.println(String.format("Total withdrawals: ₱%.2f", totalWithdrawals));
            writer.println(String.format("Net movement: ₱%.2f", totalDeposits - totalWithdrawals));
            writer.println("---------------------------------------------");

            writer.println("\n==================== END OF REPORT ====================");
            System.out.println("Transaction summary generated successfully!");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error generating transaction summary: " + e.getMessage());
        }
    }

    /**
     * Helper method to write accounts to a report file
     * @param writer PrintWriter for the report file
     * @param csvFilename CSV file containing account information
     * @param accountType Type of accounts to include in the section
     * @param statusFilter Filter accounts by status (active/closed)
     */
    private static void writeAccountsToReport(PrintWriter writer, String csvFilename, String accountType, String statusFilter) {
        File file = new File(ACCOUNTS_DIR + csvFilename);
        if (!file.exists()) return;

        writer.println("--- " + accountType + " ---");
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Get header to determine CSV structure
            String header = reader.readLine();
            String[] headerFields = header.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Check if the status matches our filter
                String status = data[data.length - 1]; // Status is the last field in all CSVs

                if (status.equalsIgnoreCase(statusFilter)) {
                    writer.println("\nAccount #: " + data[0]);
                    writer.println("Name: " + data[1]);

                    // Handle different account types with different fields
                    if (csvFilename.equals("bank_accounts.csv")) {
                        writer.println("Balance: ₱" + data[2]);
                    } else if (csvFilename.equals("investment_accounts.csv")) {
                        writer.println("Balance: ₱" + data[2]);
                        writer.println("Minimum Balance: ₱" + data[3]);
                        writer.println("Interest Rate: " + (Double.parseDouble(data[4]) * 100) + "%");
                    } else if (csvFilename.equals("checking_accounts.csv")) {
                        writer.println("Balance: ₱" + data[2]);
                        writer.println("Minimum Balance: ₱" + data[3]);
                    } else if (csvFilename.equals("credit_card_accounts.csv")) {
                        writer.println("Credit Limit: ₱" + data[2]);
                        writer.println("Current Charges: ₱" + data[3]);
                        writer.println("Available Credit: ₱" + (Double.parseDouble(data[2]) - Double.parseDouble(data[3])));
                    }
                    count++;
                }
            }

            if (count == 0) {
                writer.println("No " + statusFilter + " accounts found.");
            } else {
                writer.println("\nTotal " + statusFilter + " " + accountType + ": " + count);
            }
            writer.println();

        } catch (IOException | NumberFormatException e) {
            writer.println("Error reading account data: " + e.getMessage());
        }
    }

        /**
         * Exports a specific checking account's details to a file
         * @param account The checking account to export
         */
        public static void exportCheckingAccountDetails(CheckingAccount account) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(REPORTS_DIR + "checking_account_" +
                    account.getAccountNo() + "_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt"))) {

                writer.println("================== CHECKING ACCOUNT DETAILS ==================");
                writer.println("Generated on: " + LocalDateTime.now().format(DATE_FORMAT));
                writer.println("===========================================================\n");

                writer.println("Account Number: " + account.getAccountNo());
                writer.println("Account Name: " + account.getAccountName());
                writer.println("Current Balance: ₱" + account.inquireBalance());
                writer.println("Minimum Balance: ₱" + account.getMinimumBalance());
                writer.println("Account Status: " + account.getStatus());

                // Add transaction history
                File transactionFile = new File(TRANSACTIONS_DIR + account.getAccountNo() + "_transactions.csv");
                if (transactionFile.exists()) {
                    writer.println("\n------------------ RECENT TRANSACTIONS ------------------");
                    try (BufferedReader reader = new BufferedReader(new FileReader(transactionFile))) {
                        // Skip header
                        reader.readLine();

                        String line;
                        int count = 0;
                        while ((line = reader.readLine()) != null && count < 10) {
                            String[] data = line.split(",");
                            if (data.length >= 4) {
                                writer.println(String.format("%-20s %-15s ₱%-10s %s",
                                        data[0], data[1], data[2], data[3]));
                                count++;
                            }
                        }
                    }
                }

                writer.println("\n==================== END OF REPORT ====================");
                System.out.println("Checking account details exported successfully!");
            } catch (IOException e) {
                System.out.println("Error exporting checking account details: " + e.getMessage());
            }
        }

/**
 * Checks if a checking account's balance is below the minimum balance
 * @param checkingAccounts List of checking accounts to check
 * @return List of accounts with balance below minimum
 */
        public static ArrayList<CheckingAccount> checkLowBalanceAccounts(ArrayList<CheckingAccount> checkingAccounts) {
            ArrayList<CheckingAccount> lowBalanceAccounts = new ArrayList<>();

            for (CheckingAccount account : checkingAccounts) {
                if (account.getStatus().equals("active") &&
                        account.inquireBalance() < account.getMinimumBalance()) {
                    lowBalanceAccounts.add(account);
                }
            }

            return lowBalanceAccounts;
        }

/**
 * Generates a report of checking accounts with balance below minimum
 * @param checkingAccounts List of checking accounts
 */
        public static void generateLowBalanceReport(ArrayList<CheckingAccount> checkingAccounts) {
            ArrayList<CheckingAccount> lowBalanceAccounts = checkLowBalanceAccounts(checkingAccounts);

            if (lowBalanceAccounts.isEmpty()) {
                System.out.println("No checking accounts with balance below minimum found.");
                return;
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(REPORTS_DIR + "low_balance_accounts_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt"))) {

                writer.println("================= LOW BALANCE ACCOUNTS REPORT =================");
                writer.println("Generated on: " + LocalDateTime.now().format(DATE_FORMAT));
                writer.println("===========================================================\n");

                writer.println("The following checking accounts have balances below their minimum requirement:");
                writer.println();

                for (CheckingAccount account : lowBalanceAccounts) {
                    writer.println("Account #: " + account.getAccountNo());
                    writer.println("Name: " + account.getAccountName());
                    writer.println("Current Balance: ₱" + account.inquireBalance());
                    writer.println("Minimum Balance: ₱" + account.getMinimumBalance());
                    writer.println("Deficit: ₱" + (account.getMinimumBalance() - account.inquireBalance()));
                    writer.println("----------------------------------------------------------");
                }

                writer.println("\nTotal accounts below minimum balance: " + lowBalanceAccounts.size());
                writer.println("\n==================== END OF REPORT ====================");
                System.out.println("Low balance accounts report generated successfully!");
            } catch (IOException e) {
                System.out.println("Error generating low balance accounts report: " + e.getMessage());
            }
        }
    /**
     * Helper method to get current timestamp as string
     * @return Formatted timestamp string
     */
    private static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }

}