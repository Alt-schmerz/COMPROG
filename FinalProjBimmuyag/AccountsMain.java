package FinalProjBimmuyag;

/*
  Final Project
  Group 3
  April 5, 2025
  Balunatse, Denver C.
  Bimmuyag, Ashel John D.
  Lavarias, Ginobili D.
  Manalili, Meyielle Kkairi B.
  Rivera, Sherlie O.
  Ugay, Antonio Yzmael P.
  Valdriz, Jake Ivan T.
 */

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.ArrayList;
import java.io.File;

import java.util.Date;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;




public class AccountsMain {
    private static ArrayList<BankAccounts> bankAccounts = new ArrayList<>();
    private static ArrayList<InvestmentAccount> investmentAccounts = new ArrayList<>();
    private static ArrayList<CheckingAccount> checkingAccounts = new ArrayList<>();
    private static ArrayList<CreditCardAccount> creditCardAccounts = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Creates a savings account with a unique account number
     */
    private static void createSavingsAccount() {
        int accountNumber = generateRandomBankAccountNumber();
        System.out.println("Savings account created successfully!");
        System.out.println("Your account number is: " + accountNumber);
        // Additional savings account setup logic (e.g., storing the account, setting an initial balance, etc.) can be added here
    }

    /**
     * Saves all account data before exiting
     */
    private static void saveAllData() {
        FileManager.saveAllAccounts(bankAccounts, investmentAccounts, checkingAccounts, creditCardAccounts);
        System.out.println("All account data saved successfully!");
    }

    /**
     * Main method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) throws InvalidAmountException, InsufficientFundsException, AccountClosedException, TransactionLimitException {
        System.out.println("======================== Welcome to Group 3 Bank =======================");
        System.out.println("==================== Bank Account Management System ====================\n");

        // Initialize directories for file storage
        FileManager.initializeDirectories();

        // Load existing accounts data
        try {
            FileManager.loadAllAccounts(bankAccounts, investmentAccounts, checkingAccounts, creditCardAccounts);
            System.out.println("Loaded existing account data.");
        } catch (Exception e) {
            System.out.println("Error loading account data: " + e.getMessage());
        }

        int choice;
        do {
            displayMainMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    createAccountMenu();
                    break;
                case 2:
                    balanceInquiry();
                    break;
                case 3:
                    depositTransaction();
                    break;
                case 4:
                    withdrawalTransaction();
                    break;
                case 5:
                    transferMoney();
                    break;
                case 6:
                    displayAccountInformation();
                    break;
                case 7:
                    closeAccount();
                    break;
                case 8:
                    reopenAccount();
                    break;
                case 9:
                    generateReportsMenu();
                    break;
                case 10:
                    applyMonthlyInterestToAccounts();
                    break;
                case 11:
                    saveAllData();
                    System.out.println("Thank you for using Bank Account Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); // Extra line for readability
        } while (choice != 11);

        scanner.close();
    }

    /**
     * Displays the main menu
     */
    private static void displayMainMenu() {
        System.out.println("------------------------ Group 3 Bank Main Menu ------------------------");
        System.out.println("|              Please Select Your Transaction Type                     |");
        System.out.println("|    1) CREATE BANK ACCOUNT          6) DISPLAY ACCOUNT INFORMATION    |");
        System.out.println("|    2) BALANCE INQUIRY              7) CLOSE ACCOUNT                  |");
        System.out.println("|    3) DEPOSIT TRANSACTION          8) REOPEN ACCOUNT                 |");
        System.out.println("|    4) WITHDRAWAL TRANSACTION       9) GENERATE REPORTS               |");
        System.out.println("|    5) TRANSFER MONEY              10) APPLY MONTHLY INTEREST         |");
        System.out.println("|                                   11) EXIT                           |");
        System.out.println("------------------------------------------------------------------------");
        System.out.print("Enter your choice: ");
    }

    /**
     * Gets user choice with validation
     *
     * @return Valid user choice
     */
    private static int getUserChoice() {
        int choice = 0;
        boolean valid = false;

        while (!valid) {
            try {
                choice = scanner.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Clear the newline character
        return choice;
    }

    /**
     * Displays the reports menu
     */


    /**
     * Displays the account creation menu
     */
    private static void createAccountMenu() {
        System.out.println("\n---------------------- Account Creation Menu ----------------------");
        System.out.println("|    1) CREATE SAVINGS ACCOUNT                                     |");
        System.out.println("|    2) CREATE CHECKING ACCOUNT                                    |");
        System.out.println("|    3) CREATE INVESTMENT ACCOUNT                                  |");
        System.out.println("|    4) CREATE CREDIT CARD ACCOUNT                                 |");
        System.out.println("|    5) RETURN TO MAIN MENU                                        |");
        System.out.println("------------------------------------------------------------------");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                createBankAccount();
                break;
            case 2:
                createCheckingAccount();
                break;
            case 3:
                createInvestmentAccount();
                break;
            case 4:
                createCreditCardAccount();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    /**
     * Add this option to the generateReportsMenu() method in AccountsMain.java
     */
// Inside the generateReportsMenu() method, modify the menu display:
    private static void generateReportsMenu() {
        System.out.println("\n-------------------------- Reports Menu --------------------------");
        System.out.println("|    1) ACTIVE ACCOUNTS REPORT     4) CHECK LOW BALANCE ACCOUNTS  |");
        System.out.println("|    2) CLOSED ACCOUNTS REPORT     5) EXPORT CHECKING ACCOUNT     |");
        System.out.println("|    3) TRANSACTION SUMMARY        6) RETURN TO MAIN MENU         |");
        System.out.println("------------------------------------------------------------------");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                FileManager.generateActiveAccountsReport();
                break;
            case 2:
                FileManager.generateClosedAccountsReport();
                break;
            case 3:
                System.out.print("Enter account number for transaction summary: ");
                int accountNo = validateAccountNumber();
                FileManager.generateTransactionSummary(accountNo);
                break;
            case 4:
                FileManager.generateLowBalanceReport(checkingAccounts);
                break;
            case 5:
                exportCheckingAccountDetails();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    /**
     * Exports details of a specific checking account
     */
    private static void exportCheckingAccountDetails() {
        CheckingAccount account = selectCheckingAccount();
        if (account != null) {
            FileManager.exportCheckingAccountDetails(account);
        }
    }

    /**
     * Generates a Unique Bank Account Number
     *
     * @return Bank Account Number
     */
    private static int generateRandomBankAccountNumber() {
        Random random = new Random();
        return 100000000 + random.nextInt(100000000); // Generates a number between 100000000 and 199999999
    }

    /**
     * Generates a Unique Investment Account Number
     *
     * @return Investment Account Number
     */
    private static int generateRandomInvestmentAccountNumber() {
        Random random = new Random();
        return 200000000 + random.nextInt(100000000); // Generates a number between 200000000 and 299999999
    }

    /**
     * Generates a Unique Checking Account Number
     *
     * @return Checking Account Number
     */
    private static int generateRandomCheckingAccountNumber() {
        Random random = new Random();
        return 300000000 + random.nextInt(100000000); // Generates a number between 300000000 and 399999999
    }

    /**
     * Generates a Unique Credit Account Number
     *
     * @return Credit Account Number
     */
    private static int generateRandomCreditAccountNumber() {
        Random random = new Random();
        return 400000000 + random.nextInt(100000000); // Generates a number between 400000000 and 499999999
    }

    /**
     * Creates a regular bank account
     */
    private static void createBankAccount() {
        try {
            int accountNo;
            do {
                accountNo = generateRandomBankAccountNumber();
            } while (findAccount(accountNo) != null); // Ensure the account number is unique

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Generated account number: " + accountNo + "\n");

            System.out.print("Enter account name: ");
            String accountName = scanner.nextLine();

            bankAccounts.add(new BankAccounts(accountNo, accountName)); // Add to ArrayList
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Bank account created successfully!");

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    /**
     * Creates an investment account
     */
    private static void createInvestmentAccount() {
        try {
            int accountNo;
            do {
                accountNo = generateRandomInvestmentAccountNumber();
            } while (findAccount(accountNo) != null); // Ensure the account number is unique

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Generated account number: " + accountNo + "\n");

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter account name: ");
            String accountName = scanner.nextLine();

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter minimum balance: ₱");
            double minimumBalance = validatePositiveDouble();

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter interest rate (e.g., 0.05 for 5%): ");
            double interest = validatePositiveDouble();

            investmentAccounts.add(new InvestmentAccount(accountNo, accountName, minimumBalance, interest));
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Investment account created successfully!");

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    /**
     * Creates a checking account
     */
    private static void createCheckingAccount() {
        try {
            int accountNo;
            do {
                accountNo = generateRandomCheckingAccountNumber();
            } while (findAccount(accountNo) != null); // Ensure the account number is unique

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Generated account number: " + accountNo + "\n");

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter account name: ");
            String accountName = scanner.nextLine();

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter minimum balance: ₱");
            double minimumBalance = validatePositiveDouble();

            checkingAccounts.add(new CheckingAccount(accountNo, accountName, minimumBalance));
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Checking account created successfully!");

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    /**
     * Creates a credit card account
     */
    private static void createCreditCardAccount() {
        try {
            int accountNo;
            do {
                accountNo = generateRandomCreditAccountNumber();
            } while (findAccount(accountNo) != null); // Ensure the account number is unique

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Generated account number: " + accountNo + "\n");

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter account name: ");
            String accountName = scanner.nextLine();

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter credit limit: ₱");
            double creditLimit = validatePositiveDouble();

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Enter initial charges (0 for none): ₱");
            double charges = validateNonNegativeDouble();

            creditCardAccounts.add(new CreditCardAccount(accountNo, accountName, creditLimit, charges));
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Credit card account created successfully!");

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    /**
     * Displays balance inquiry menu and handles account selection
     */
    private static void balanceInquiry() {
        System.out.println("\n============================ Balance Inquiry ========================");
        BankAccounts account = selectAccount();

        if (account != null) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("\nAccount Information:");
            System.out.println("Account Number: " + account.getAccountNo());
            System.out.println("Account Name: " + account.getAccountName());

            if (account instanceof InvestmentAccount) {
                ((InvestmentAccount) account).inquireInvestmentValue();
            } else if (account instanceof CreditCardAccount) {
                ((CreditCardAccount) account).inquireAvailableCredit();
            } else {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Current Balance: ₱" + account.inquireBalance());
            }
        }
    }

    /**
     * Displays deposit transaction menu and handles account selection
     */
    private static void depositTransaction() throws InvalidAmountException, AccountClosedException {
        System.out.println("\n======================== Deposit Transaction ==========================");
        BankAccounts account = selectAccount();

        if (account != null) {
            if (account instanceof CreditCardAccount) {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Credit card accounts use payCard instead of deposit.");
                System.out.print("Would you like to make a payment? (y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("y")) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.print("Enter payment amount: ₱");
                    double amount = validatePositiveDouble();
                    ((CreditCardAccount) account).payCard(amount);
                }
            } else if (account instanceof InvestmentAccount) {
                System.out.println("------------------------------------------------------------------------");
                System.out.print("Enter investment amount: ₱");
                double amount = validatePositiveDouble();
                try {
                    ((InvestmentAccount) account).addInvestment(amount);
                } catch (InvalidAmountException | AccountClosedException e) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("------------------------------------------------------------------------");
                System.out.print("Enter deposit amount: ₱");
                double amount = validatePositiveDouble();
                try {
                    account.deposit(amount);
                } catch (InvalidAmountException | AccountClosedException e) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Displays withdrawal transaction menu and handles account selection
     */
    private static void withdrawalTransaction() throws InvalidAmountException, InsufficientFundsException, AccountClosedException, TransactionLimitException {
        System.out.println("\n======================== Withdrawal Transaction =======================");
        BankAccounts account = selectAccount();

        if (account != null) {
            if (account instanceof InvestmentAccount) {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Withdrawals are not allowed for Investment Accounts.");
            } else if (account instanceof CreditCardAccount) {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Direct withdrawals are not allowed for Credit Card Accounts.");
                System.out.print("Would you like to get a cash advance instead? (y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("y")) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.print("Enter cash advance amount: ₱");
                    double amount = validatePositiveDouble();
                    ((CreditCardAccount) account).getCashAdvance(amount);
                }
            } else if (account instanceof CheckingAccount) {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Direct withdrawals are not allowed for Checking Accounts.");
                System.out.print("Would you like to encash a check instead? (y/n): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("y")) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.print("Enter check amount: ₱");
                    double amount = validatePositiveDouble();
                    ((CheckingAccount) account).encashCheck(amount);
                }
            } else {
                System.out.println("------------------------------------------------------------------------");
                System.out.print("Enter withdrawal amount: ₱");
                double amount = validatePositiveDouble();
                account.withdraw(amount);
            }
        }
    }

    /**
     * Displays transfer money menu and handles account selection
     */
    private static void transferMoney() {
        System.out.println("\n============================ Transfer Money ===========================");
        BankAccounts sourceAccount = selectAccount();

        if (sourceAccount == null) {
            return;
        }

        if (sourceAccount instanceof InvestmentAccount || sourceAccount instanceof CreditCardAccount) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("This type of account cannot transfer money.");
            return;
        }

        int targetAccountNo = validateAccountNumber();

        BankAccounts targetAccount = findAccount(targetAccountNo);

        if (targetAccount == null) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Target account not found.");
            return;
        }

        if (targetAccount.getStatus().equals("closed")) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Target account is closed.");
            return;
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.print("Enter transfer amount: ₱");
        double amount = validatePositiveDouble();

        if (sourceAccount.inquireBalance() < amount) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Insufficient balance for transfer.");
            return;
        }

        // Perform the transfer
        sourceAccount.setBalance(sourceAccount.inquireBalance() - amount);
        targetAccount.receiveTransfer(amount);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Transfer of ₱" + amount + " completed successfully.");

// Log the outgoing transfer
        FileManager.logTransaction(sourceAccount.getAccountNo(), "Transfer Out", amount,
                "Transfer to account #" + targetAccount.getAccountNo());
    }

    /**
     * Displays account information menu and handles account selection
     */
    private static void displayAccountInformation() {
        System.out.println("\n------------------------ Select Account Type ------------------------");
        System.out.println("|                   1) DISPLAY A SPECIFIC ACCOUNT                   |");
        System.out.println("|                   2) DISPLAY ALL ACCOUNTS                         |");
        System.out.println("---------------------------------------------------------------------");
        System.out.print("Enter your choice: ");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                BankAccounts account = selectAccount();
                if (account != null) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.println("\nAccount Details:");
                    System.out.println(account.getClass().getSimpleName() + ": " + String.valueOf(account));
                }
                break;
            case 2:
                displayAllAccounts();
                break;
            default:
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Invalid choice.");
        }
    }

    /**
     * Displays all accounts in the system
     */
    private static void displayAllAccounts() {
        boolean anyAccounts = false;
        System.out.println("\n=========================== All Bank Accounts ==========================");
        for (BankAccounts account : bankAccounts) {
            System.out.println("\nRegular Bank Account");
            System.out.println(account.toString());
            anyAccounts = true;
        }

        System.out.println("\n======================== All Investment Accounts =======================");
        for (InvestmentAccount account : investmentAccounts) {
            System.out.println("\nInvestment Account");
            System.out.println(account.toString());
            anyAccounts = true;
        }

        System.out.println("\n========================= All Checking Accounts ========================");
        for (CheckingAccount account : checkingAccounts) {
            System.out.println("\nChecking Account");
            System.out.println(account.toString());
            anyAccounts = true;
        }

        System.out.println("\n======================= All Credit Card Accounts =======================");
        for (CreditCardAccount account : creditCardAccounts) {
            System.out.println("\nCredit Card Account");
            System.out.println(account.toString());
            anyAccounts = true;
        }

        if (!anyAccounts) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("No accounts have been created yet.");
        }
    }

    /**
     * Displays close account menu and handles account selection
     */
    private static void closeAccount() {
        System.out.println("\n============================ Close Account =============================");
        BankAccounts account = selectAccount();

        if (account != null) {
            if (account.getStatus().equals("closed")) {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("This account is already closed.");
                return;
            }

            System.out.println("------------------------------------------------------------------------");
            System.out.print("Are you sure you want to close this account? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("y")) {
                account.closeAccount();
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Account closed successfully.");
            } else {
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Account closure cancelled.");
            }
        }
    }

    /**
     * Selects an account from various account types
     *
     * @return The selected account or null if no account was selected
     */
    private static BankAccounts selectAccount() {
        System.out.println("------------------------ Select Account Type ------------------------");
        System.out.println("|    1) REGULAR BANK ACCOUNT      3) CHECKING ACCOUNT               |");
        System.out.println("|    2) INVESTMENT ACCOUNT        4) CREDIT CART ACCOUNT            |");
        System.out.println("---------------------------------------------------------------------");
        System.out.print("Enter your choice: ");

        int typeChoice = getUserChoice();

        switch (typeChoice) {
            case 1:
                return selectBankAccount();
            case 2:
                return selectInvestmentAccount();
            case 3:
                return selectCheckingAccount();
            case 4:
                return selectCreditCardAccount();
            default:
                System.out.println("------------------------------------------------------------------------");
                System.out.println("Invalid choice.");
                return null;
        }
    }

    /**
     * Selects a regular bank account
     *
     * @return The selected bank account or null if none selected
     */
    private static BankAccounts selectBankAccount() {
        if (bankAccounts.isEmpty()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("No bank accounts have been created yet.");
            return null;
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nAvailable Bank Accounts:");
        for (int i = 0; i < bankAccounts.size(); i++) {
            System.out.println((i + 1) + ". Account #" + bankAccounts.get(i).getAccountNo() +
                    " - " + bankAccounts.get(i).getAccountName() +
                    " (Status: " + bankAccounts.get(i).getStatus() + ")");
        }

        System.out.print("Select account (1-" + bankAccounts.size() + "): ");
        int accountChoice = getUserChoice();

        if (accountChoice < 1 || accountChoice > bankAccounts.size()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Invalid account selection.");
            return null;
        }

        return bankAccounts.get(accountChoice - 1);
    }

    /**
     * Selects an investment account
     *
     * @return The selected investment account or null if none selected
     */
    private static InvestmentAccount selectInvestmentAccount() {
        if (investmentAccounts.isEmpty()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("No investment accounts have been created yet.");
            return null;
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nAvailable Investment Accounts:");
        for (int i = 0; i < investmentAccounts.size(); i++) {
            System.out.println((i + 1) + ". Account #" + investmentAccounts.get(i).getAccountNo() +
                    " - " + investmentAccounts.get(i).getAccountName() +
                    " (Status: " + investmentAccounts.get(i).getStatus() + ")");
        }

        System.out.print("Select account (1-" + investmentAccounts.size() + "): ");
        int accountChoice = getUserChoice();

        if (accountChoice < 1 || accountChoice > investmentAccounts.size()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Invalid account selection.");
            return null;
        }

        return investmentAccounts.get(accountChoice - 1);
    }

    /**
     * Selects a checking account
     *
     * @return The selected checking account or null if none selected
     */
    private static CheckingAccount selectCheckingAccount() {
        if (checkingAccounts.isEmpty()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("No checking accounts have been created yet.");
            return null;
        }

        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nAvailable Checking Accounts:");
        for (int i = 0; i < checkingAccounts.size(); i++) {
            System.out.println((i + 1) + ". Account #" + checkingAccounts.get(i).getAccountNo() +
                    " - " + checkingAccounts.get(i).getAccountName() +
                    " (Status: " + checkingAccounts.get(i).getStatus() + ")");
        }

        System.out.print("Select account (1-" + checkingAccounts.size() + "): ");
        int accountChoice = getUserChoice();

        if (accountChoice < 1 || accountChoice > checkingAccounts.size()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Invalid account selection.");
            return null;
        }

        return checkingAccounts.get(accountChoice - 1);
    }

    /**
     * Selects a credit card account
     *
     * @return The selected credit card account or null if none selected
     */
    private static CreditCardAccount selectCreditCardAccount() {
        if (creditCardAccounts.isEmpty()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("No credit card accounts have been created yet.");
            return null;
        }

        System.out.println("\nAvailable Credit Card Accounts:");
        for (int i = 0; i < creditCardAccounts.size(); i++) {
            System.out.println((i + 1) + ". Account #" + creditCardAccounts.get(i).getAccountNo() +
                    " - " + creditCardAccounts.get(i).getAccountName() +
                    " (Status: " + creditCardAccounts.get(i).getStatus() + ")");
        }

        System.out.print("Select account (1-" + creditCardAccounts.size() + "): ");
        int accountChoice = getUserChoice();

        if (accountChoice < 1 || accountChoice > creditCardAccounts.size()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Invalid account selection.");
            return null;
        }

        return creditCardAccounts.get(accountChoice - 1);
    }

    /**
     * Finds an account by account number
     *
     * @param accountNo The account number to search for
     * @return The found account or null if not found
     */
    private static BankAccounts findAccount(int accountNo) {
        for (BankAccounts account : bankAccounts) {
            if (account.getAccountNo() == accountNo) {
                return account;
            }
        }
        for (InvestmentAccount account : investmentAccounts) {
            if (account.getAccountNo() == accountNo) {
                return account;
            }
        }
        for (CheckingAccount account : checkingAccounts) {
            if (account.getAccountNo() == accountNo) {
                return account;
            }
        }
        for (CreditCardAccount account : creditCardAccounts) {
            if (account.getAccountNo() == accountNo) {
                return account;
            }
        }
        return null; // Account not found
    }

    /**
     * Validates that input is a positive double
     *
     * @return A valid positive double value
     */
    private static double validatePositiveDouble() {
        double value = 0;
        boolean valid = false;

        while (!valid) {
            try {
                value = scanner.nextDouble();
                scanner.nextLine(); // Clear the newline character

                if (value <= 0) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.print("Value must be positive. Please try again: ");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("------------------------------------------------------------------------");
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        return value;
    }

    /**
     * Validates that input is a non-negative double
     *
     * @return A valid non-negative double value
     */
    private static double validateNonNegativeDouble() {
        double value = 0;
        boolean valid = false;

        while (!valid) {
            try {
                value = scanner.nextDouble();
                scanner.nextLine(); // Clear the newline character

                if (value < 0) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.print("Value cannot be negative. Please try again: ");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("------------------------------------------------------------------------");
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        return value;
    }

    /**
     * Validates account number is 9 digits
     *
     * @return A valid 9-digit account number
     */
    private static int validateAccountNumber() {
        int accountNo = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter target account number: ");
            if (scanner.hasNextInt()) {
                accountNo = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character

                try {
                    // Check if account number is 9 digits
                    if (accountNo < 100000000 || accountNo > 999999999) {
                        System.out.println("------------------------------------------------------------------------");
                        throw new IllegalArgumentException("Account number must be 9 digits.");
                    } else {
                        valid = true; // Valid account number
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("------------------------------------------------------------------------");
                    System.out.println(e.getMessage()); // Print the exception message
                }
            } else {
                System.out.println("------------------------------------------------------------------------");
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        return accountNo;
    }

    /**
     * Applies monthly interest to all active investment accounts
     */
    private static void applyMonthlyInterestToAccounts() {
        System.out.println("\n================== Apply Monthly Interest ===================");
        boolean anyApplied = false;

        for (InvestmentAccount account : investmentAccounts) {
            if (account.getStatus().equals("active")) {
                double interestAmount = account.applyMonthlyInterest();
                System.out.println("Applied interest of ₱" + interestAmount + " to account #" +
                        account.getAccountNo() + " (" + account.getAccountName() + ")");
                anyApplied = true;
            }
        }

        if (!anyApplied) {
            System.out.println("No active investment accounts found to apply interest to.");
        } else {
            System.out.println("Monthly interest applied successfully to all active investment accounts.");
        }
    }

    //OPENING THE CLOSED ACCOUNT

    /**
     * Handles reopening a previously closed account
     */
    private static void reopenAccount() {
        System.out.println("\n========================= Reopen Closed Account ==========================");
        System.out.println("Please select the closed account you want to reopen:");

        // Get all closed accounts across all account types
        ArrayList<BankAccounts> closedAccounts = new ArrayList<>();

        // Add closed bank accounts
        for (BankAccounts account : bankAccounts) {
            if (account.getStatus().equals("closed")) {
                closedAccounts.add(account);
            }
        }

        // Add closed investment accounts
        for (InvestmentAccount account : investmentAccounts) {
            if (account.getStatus().equals("closed")) {
                closedAccounts.add(account);
            }
        }

        // Add closed checking accounts
        for (CheckingAccount account : checkingAccounts) {
            if (account.getStatus().equals("closed")) {
                closedAccounts.add(account);
            }
        }

        // Add closed credit card accounts
        for (CreditCardAccount account : creditCardAccounts) {
            if (account.getStatus().equals("closed")) {
                closedAccounts.add(account);
            }
        }

        if (closedAccounts.isEmpty()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("No closed accounts found to reopen.");
            return;
        }

        // Display closed accounts
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nClosed Accounts:");
        for (int i = 0; i < closedAccounts.size(); i++) {
            BankAccounts account = closedAccounts.get(i);
            System.out.println((i + 1) + ". Account #" + account.getAccountNo() +
                    " - " + account.getAccountName() +
                    " (Type: " + getAccountType(account) + ")");
        }

        System.out.print("Select account to reopen (1-" + closedAccounts.size() + "): ");
        int accountChoice = getUserChoice();

        if (accountChoice < 1 || accountChoice > closedAccounts.size()) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Invalid account selection.");
            return;
        }

        BankAccounts selectedAccount = closedAccounts.get(accountChoice - 1);

        // Reopen the account
        selectedAccount.reopenAccount();

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Account #" + selectedAccount.getAccountNo() + " has been reopened successfully!");

        // Log the reopen transaction
        FileManager.logTransaction(selectedAccount.getAccountNo(), "Account Reopened", 0.0,
                "Account reopened.");
    }

    /**
     * Helper method to determine account type as string
     */
    private static String getAccountType(BankAccounts account) {
        if (account instanceof InvestmentAccount) {
            return "Investment";
        } else if (account instanceof CheckingAccount) {
            return "Checking";
        } else if (account instanceof CreditCardAccount) {
            return "Credit Card";
        } else {
            return "Regular Bank";
        }
    }
}