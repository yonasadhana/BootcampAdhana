package com.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * UserInterface Class
 *
 * This class manages all user interactions for the Accounting Ledger application.
 * It provides menu displays, handles user input, and coordinates with the Ledger
 * class to perform financial transactions and generate reports.
 */
public class UserInterface {
    private Scanner inputScanner = new Scanner(System.in);
    private Ledger transactionLedger;

    public UserInterface(Ledger transactionLedger) {
        this.transactionLedger = transactionLedger;
    }

    /**
     * HOME SCREEN
     *
     * Displays the main menu of the application and processes user selections.
     * This is the entry point of the application where users can:
     * - Add deposits
     * - Make payments
     * - Access the ledger for viewing transactions
     * - Exit the application
     */
    public void displayHomeScreen() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n" + "╔═════════════════════════════════════════╗\n" + "║          ACCOUNTING LEDGER              ║\n" + "╠═════════════════════════════════════════╣\n" + "║  D) Add Deposit                         ║\n" + "║  P) Make Payment (Debit)                ║\n" + "║  L) View Ledger                         ║\n" + "║  X) Exit Application                    ║\n" + "╚═════════════════════════════════════════╝");

            System.out.println("To select an option, type the letter and press Enter.");
            System.out.print("Your choice: ");

            String userChoice = inputScanner.nextLine();

            if (userChoice != null && !userChoice.isEmpty()) {
                switch (userChoice.toUpperCase()) {
                    case "D":
                        addDeposit();
                        break;
                    case "P":
                        makePayment();
                        break;
                    case "L":
                        displayLedgerScreen();
                        break;
                    case "X":
                        System.out.println("╔═════════════════════════════════════════╗\n" + "║      THANK YOU FOR USING THE            ║\n" + "║     ACCOUNTING LEDGER APPLICATION       ║\n" + "╚═════════════════════════════════════════╝");
                        // Exit the application completely
                        System.exit(0); // This will terminate the program immediately
                        break;
                    default:
                        System.out.println("! Invalid option. Please enter D, P, L, or X.");
                }
            } else {
                System.out.println("! Please enter a selection.");
            }
        }
    }

    /**
     * TRANSACTION ENTRY: ADD DEPOSIT
     *
     * Collects information from the user to create a new deposit transaction.
     * Gathers description, vendor, and positive amount from the user.
     * Adds the transaction to the ledger with current date and time.
     * Handles input validation and error cases.
     */
    private void addDeposit() {
        try {
            System.out.println("\n╔═════════════════════════════════════════╗");
            System.out.println("║              ADD DEPOSIT                ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Please provide the following information:");

            System.out.print("Description (what is this deposit for?): ");
            String transactionDescription = inputScanner.nextLine();

            System.out.print("Vendor/Source (where did the money come from?): ");
            String vendorName = inputScanner.nextLine();

            System.out.print("Amount (positive number only): $");
            double transactionAmount = Double.parseDouble(inputScanner.nextLine());

            if (transactionAmount <= 0) {
                System.out.println("! Deposit amount must be positive. Transaction cancelled.");
                showPostTaskOptions("deposit");
                return;
            }

            Transaction depositTransaction = new Transaction(LocalDate.now(), LocalTime.now(), transactionDescription, vendorName, transactionAmount);
            transactionLedger.addTransaction(depositTransaction);

            System.out.println("\n+ Deposit added successfully!");
            showPostTaskOptions("deposit");

        } catch (NumberFormatException formatException) {
            System.out.println("! Invalid amount format. Please enter a valid number.");
            showPostTaskOptions("deposit");
        } catch (Exception generalException) {
            System.out.println("! Error adding deposit: " + generalException.getMessage());
            showPostTaskOptions("deposit");
        }
    }

    /**
     * TRANSACTION ENTRY: MAKE PAYMENT
     *
     * Collects information from the user to create a new payment transaction.
     * Gathers description, vendor, and positive amount from the user.
     * Converts the amount to negative before storing.
     * Adds the transaction to the ledger with current date and time.
     * Handles input validation and error cases.
     */
    private void makePayment() {
        try {
            System.out.println("\n╔═════════════════════════════════════════╗");
            System.out.println("║             MAKE PAYMENT                ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Please provide the following information:");

            System.out.print("Description (what is this payment for?): ");
            String transactionDescription = inputScanner.nextLine();

            System.out.print("Vendor/Recipient (who receives this payment?): ");
            String vendorName = inputScanner.nextLine();

            System.out.print("Amount (positive number only): $");
            double transactionAmount = Double.parseDouble(inputScanner.nextLine());

            if (transactionAmount <= 0) {
                System.out.println("! Payment amount must be positive. Transaction cancelled.");
                showPostTaskOptions("payment");
                return;
            }

            transactionAmount = -transactionAmount;

            Transaction paymentTransaction = new Transaction(LocalDate.now(), LocalTime.now(), transactionDescription, vendorName, transactionAmount);
            transactionLedger.addTransaction(paymentTransaction);

            System.out.println("\n+ Payment recorded successfully!");
            showPostTaskOptions("payment");

        } catch (NumberFormatException formatException) {
            System.out.println("! Invalid amount format. Please enter a valid number.");
            showPostTaskOptions("payment");
        } catch (Exception generalException) {
            System.out.println("! Error making payment: " + generalException.getMessage());
            showPostTaskOptions("payment");
        }
    }

    /**
     * LEDGER MENU
     *
     * Displays the ledger options menu and processes user selections.
     * Provides access to different views of transaction data:
     * - All transactions
     * - Only deposits
     * - Only payments
     * - Advanced reporting menu
     * - Return to main menu
     */
    private void displayLedgerScreen() {
        boolean isViewingLedger = true;

        while (isViewingLedger) {
            System.out.println("\n" + "╔═════════════════════════════════════════╗\n" + "║              LEDGER MENU                ║\n" + "╠═════════════════════════════════════════╣\n" + "║  A) All Entries                         ║\n" + "║  D) Deposits Only                       ║\n" + "║  P) Payments Only                       ║\n" + "║  R) Reports                             ║\n" + "║  H) Return to Home                      ║\n" + "╚═════════════════════════════════════════╝");

            System.out.println("Type a letter and press Enter to continue.");
            System.out.print("Your choice: ");

            String userChoice = inputScanner.nextLine();

            if (userChoice != null && !userChoice.isEmpty()) {
                switch (userChoice.toUpperCase()) {
                    case "A":
                        displayAllEntries();
                        break;
                    case "D":
                        displayDeposits();
                        break;
                    case "P":
                        displayPayments();
                        break;
                    case "R":
                        displayReportsScreen();
                        break;
                    case "H":
                        isViewingLedger = false;
                        break;
                    default:
                        System.out.println("! Invalid option. Please enter A, D, P, R, or H.");
                }
            } else {
                System.out.println("! Please enter a selection.");
            }
        }
    }

    /**
     * LEDGER VIEW: ALL ENTRIES
     *
     * Displays a complete list of all financial transactions in the ledger.
     * Shows transactions in reverse chronological order (newest first).
     * Includes deposits and payments in a single view.
     */
    private void displayAllEntries() {
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║             ALL ENTRIES                 ║");
        System.out.println("╚═════════════════════════════════════════╝");

        ArrayList<Transaction> transactionsList = transactionLedger.getAllTransactions();

        if (transactionsList.isEmpty()) {
            System.out.println("No transactions found.");
            showPostTaskOptions("ledger");
            return;
        }

        displayTransactionList(transactionsList);
        showPostTaskOptions("ledger");
    }

    /**
     * LEDGER VIEW: DEPOSITS ONLY
     *
     * Displays only deposit transactions (positive amounts).
     * Shows transactions in reverse chronological order (newest first).
     * Useful for tracking income sources.
     */
    private void displayDeposits() {
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║               DEPOSITS                  ║");
        System.out.println("╚═════════════════════════════════════════╝");

        ArrayList<Transaction> depositsList = transactionLedger.getDeposits();

        if (depositsList.isEmpty()) {
            System.out.println("No deposits found.");
            showPostTaskOptions("ledger");
            return;
        }

        displayTransactionList(depositsList);
        showPostTaskOptions("ledger");
    }

    /**
     * LEDGER VIEW: PAYMENTS ONLY
     *
     * Displays only payment transactions (negative amounts).
     * Shows transactions in reverse chronological order (newest first).
     * Useful for tracking expenses.
     */
    private void displayPayments() {
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║               PAYMENTS                  ║");
        System.out.println("╚═════════════════════════════════════════╝");

        ArrayList<Transaction> paymentsList = transactionLedger.getPayments();

        if (paymentsList.isEmpty()) {
            System.out.println("No payments found.");
            showPostTaskOptions("ledger");
            return;
        }

        displayTransactionList(paymentsList);
        showPostTaskOptions("ledger");
    }

    /**
     * TRANSACTION DISPLAY HELPER
     *
     * Formats and displays a list of transactions in a tabular format.
     * Handles text wrapping for long descriptions over multiple lines.
     * Shows date, time, description, vendor, and amount in aligned columns.
     * Orders transactions from newest to oldest.
     */
    private void displayTransactionList(ArrayList<Transaction> transactionsList) {
        String tableDivider = "================================================================================================================";
        System.out.println(tableDivider);
        System.out.println("   DATE      |    TIME    |             DESCRIPTION              |             VENDOR             |      AMOUNT");
        System.out.println(tableDivider);

        // Sort transactions by date in descending order (newest first)
        transactionsList.sort((t1, t2) -> {
            // First compare by date
            int dateComparison = t2.getDate().compareTo(t1.getDate());
            if (dateComparison != 0) {
                return dateComparison;
            }
            // If dates are equal, compare by time
            return t2.getTime().compareTo(t1.getTime());
        });

        // Display transactions in the sorted order
        for (Transaction item : transactionsList) {
            String description = item.getDescription();
            String vendor = item.getVendor();

            if (description.length() > 35) {
                int descMaxLength = 35;
                String firstLine = description.substring(0, descMaxLength);
                String secondLine = description.substring(descMaxLength);

                System.out.printf(" %-10s | %-10s | %-35s | %-30s | $%14.2f%n", item.getDate(), item.getTime().toString().substring(0, 8), firstLine, vendor, item.getAmount());

                System.out.printf(" %-10s | %-10s | %-35s | %-30s | %15s%n", "          ", "          ", secondLine, "", "");
            } else {
                System.out.printf(" %-10s | %-10s | %-35s | %-30s | $%14.2f%n", item.getDate(), item.getTime().toString().substring(0, 8), description, vendor, item.getAmount());
            }
        }

        System.out.println(tableDivider);
    }

    /**
     * REPORTS MENU
     *
     * Displays the reporting options menu and processes user selections.
     * Provides access to various financial reports:
     * - Month-to-date transactions
     * - Previous month transactions
     * - Year-to-date transactions
     * - Previous year transactions
     * - Search for transactions by vendor
     */
    private void displayReportsScreen() {
        boolean isViewingReports = true;

        while (isViewingReports) {
            System.out.println("\n" + "╔═════════════════════════════════════════╗\n" + "║             REPORTS MENU                ║\n" + "╠═════════════════════════════════════════╣\n" + "║  1) Month To Date                       ║\n" + "║  2) Previous Month                      ║\n" + "║  3) Year To Date                        ║\n" + "║  4) Previous Year                       ║\n" + "║  5) Search by Vendor                    ║\n" + "║  0) Back to Ledger Menu                 ║\n" + "╚═════════════════════════════════════════╝");

            System.out.println("Type a number and press Enter to continue.");
            System.out.print("Your choice: ");

            String userChoice = inputScanner.nextLine();

            if (userChoice == null || userChoice.isEmpty()) {
                System.out.println("! Please enter a selection.");
                continue;
            }

            userChoice = userChoice.trim();

            switch (userChoice) {
                case "1":
                    reportMonthToDate();
                    break;
                case "2":
                    reportPreviousMonth();
                    break;
                case "3":
                    reportYearToDate();
                    break;
                case "4":
                    reportPreviousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    isViewingReports = false;
                    break;
                default:
                    System.out.println("! Invalid option. Please enter a number from 0-5.");
            }
        }
    }

    /**
     * REPORT: MONTH TO DATE
     *
     * Generates a financial report for the current month.
     * Shows all transactions from the beginning of the current month to today.
     * Displays transaction details and calculated totals.
     * Provides a summary indicating positive or negative cash flow.
     */
    private void reportMonthToDate() {
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║          MONTH TO DATE REPORT           ║");
        System.out.println("╚═════════════════════════════════════════╝");

        LocalDate currentDate = LocalDate.now();
        LocalDate monthStartDate = currentDate.withDayOfMonth(1);
        System.out.println("Period: " + monthStartDate + " to " + currentDate);

        ArrayList<Transaction> filteredTransactions = transactionLedger.getMonthToDateTransactions();
        double totalAmount = transactionLedger.calculateTotal(filteredTransactions);

        displayTransactionsWithTotal(filteredTransactions, totalAmount);
        showPostTaskOptions("report");
    }

    /**
     * REPORT: PREVIOUS MONTH
     *
     * Generates a financial report for the previous calendar month.
     * Shows all transactions from the previous month.
     * Displays transaction details and calculated totals.
     * Provides a summary indicating positive or negative cash flow.
     */
    private void reportPreviousMonth() {
        YearMonth previousMonth = YearMonth.from(LocalDate.now()).minusMonths(1);

        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║         PREVIOUS MONTH REPORT           ║");
        System.out.println("╚═════════════════════════════════════════╝");

        System.out.println("Period: " + previousMonth.atDay(1) + " to " + previousMonth.atEndOfMonth());

        ArrayList<Transaction> filteredTransactions = transactionLedger.getPreviousMonthTransactions();
        double totalAmount = transactionLedger.calculateTotal(filteredTransactions);

        displayTransactionsWithTotal(filteredTransactions, totalAmount);
        showPostTaskOptions("report");
    }

    /**
     * REPORT: YEAR TO DATE
     *
     * Generates a financial report for the current year.
     * Shows all transactions from January 1st to today.
     * Displays transaction details and calculated totals.
     * Provides a summary indicating positive or negative cash flow.
     */
    private void reportYearToDate() {
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║          YEAR TO DATE REPORT            ║");
        System.out.println("╚═════════════════════════════════════════╝");

        LocalDate currentDate = LocalDate.now();
        LocalDate yearStartDate = currentDate.withDayOfYear(1);
        System.out.println("Period: " + yearStartDate + " to " + currentDate);

        ArrayList<Transaction> filteredTransactions = transactionLedger.getYearToDateTransactions();
        double totalAmount = transactionLedger.calculateTotal(filteredTransactions);

        displayTransactionsWithTotal(filteredTransactions, totalAmount);
        showPostTaskOptions("report");
    }

    /**
     * REPORT: PREVIOUS YEAR
     *
     * Generates a financial report for the previous calendar year.
     * Shows all transactions from the previous year.
     * Displays transaction details and calculated totals.
     * Provides a summary indicating positive or negative cash flow.
     */
    private void reportPreviousYear() {
        int previousYear = LocalDate.now().getYear() - 1;

        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║          PREVIOUS YEAR REPORT           ║");
        System.out.println("╚═════════════════════════════════════════╝");

        System.out.println("Period: " + LocalDate.of(previousYear, 1, 1) + " to " + LocalDate.of(previousYear, 12, 31));

        ArrayList<Transaction> filteredTransactions = transactionLedger.getPreviousYearTransactions();
        double totalAmount = transactionLedger.calculateTotal(filteredTransactions);

        displayTransactionsWithTotal(filteredTransactions, totalAmount);
        showPostTaskOptions("report");
    }

    /**
     * SEARCH: BY VENDOR
     *
     * Allows searching for transactions by vendor name.
     * Prompts user for a vendor name to search for.
     * Displays matching transactions and calculates totals.
     * Useful for tracking spending with specific vendors.
     */
    private void searchByVendor() {
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║            VENDOR SEARCH                ║");
        System.out.println("╚═════════════════════════════════════════╝");

        System.out.print("Enter vendor name to search: ");
        String searchVendorName = inputScanner.nextLine().trim();

        if (searchVendorName.isEmpty()) {
            System.out.println("! Search cancelled. Please enter a vendor name.");
            showPostTaskOptions("search");
            return;
        }

        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║            SEARCH RESULTS               ║");
        System.out.println("╚═════════════════════════════════════════╝");
        System.out.println("Vendor: " + searchVendorName);

        ArrayList<Transaction> searchResults = transactionLedger.searchByVendor(searchVendorName);
        double totalAmount = transactionLedger.calculateTotal(searchResults);

        displayTransactionsWithTotal(searchResults, totalAmount);
        showPostTaskOptions("search");
    }

    /**
     * TRANSACTION DISPLAY WITH TOTALS
     *
     * Displays a list of transactions with calculated totals.
     * Shows transaction details in a tabular format.
     * Adds a summary row with the net total of all transactions.
     * Provides a brief financial assessment (positive/negative/balanced).
     * Used by all reports to display results with consistent formatting.
     */
    private void displayTransactionsWithTotal(ArrayList<Transaction> transactionsList, double totalAmount) {
        if (transactionsList.isEmpty()) {
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║         NO TRANSACTIONS FOUND           ║");
            System.out.println("╚═════════════════════════════════════════╝");
            return;
        }

        displayTransactionList(transactionsList);

        String tableDivider = "================================================================================================================";
        System.out.printf("%88s: $%14.2f%n", "TOTAL", totalAmount);
        System.out.println(tableDivider);

        System.out.println("\nSUMMARY: " + (totalAmount > 0 ? "Net income is positive!" : totalAmount < 0 ? "Net income is negative." : "Balanced budget."));
    }

    /**
     * TEXT TRUNCATION UTILITY
     *
     * Limits a string to a maximum length and adds ellipsis if needed.
     * Used primarily for displaying search headers with potentially long vendor names.
     */
    private String limitString(String textString, int maxLength) {
        if (textString == null) {
            return "";
        }

        if (textString.length() <= maxLength) {
            return textString;
        } else {
            return textString.substring(0, maxLength - 3) + "...";
        }
    }

    /**
     * NAVIGATION: POST-TASK OPTIONS
     *
     * Displays a menu of navigation options after completing a task.
     * Different options are shown based on the task type:
     * - After deposits or payments: options to add more or return home
     * - After reports: options to view more reports or navigate to other menus
     * - After searches: options to search again or navigate to other menus
     * - After ledger views: options to view more data or return home
     *
     * Handles the user's choice and redirects to the appropriate screen.
     */
    private void showPostTaskOptions(String taskType) {
        System.out.println("\n╔═════════════════════════════════════════╗");
        System.out.println("║      WHAT WOULD YOU LIKE TO DO NEXT?    ║");
        System.out.println("╚═════════════════════════════════════════╝");

        switch (taskType) {
            case "deposit":
                System.out.println("1) Add another deposit");
                System.out.println("2) Return to Home screen");
                break;
            case "payment":
                System.out.println("1) Make another payment");
                System.out.println("2) Return to Home screen");
                break;
            case "report":
                System.out.println("1) View another report");
                System.out.println("2) Return to Reports menu");
                System.out.println("3) Return to Ledger menu");
                System.out.println("4) Return to Home screen");
                break;
            case "search":
                System.out.println("1) Search for another vendor");
                System.out.println("2) Return to Reports menu");
                System.out.println("3) Return to Ledger menu");
                System.out.println("4) Return to Home screen");
                break;
            case "ledger":
                System.out.println("1) Return to Ledger menu");
                System.out.println("2) Return to Home screen");
                break;
            default:
                System.out.println("1) Continue");
                System.out.println("2) Return to Home screen");
        }

        System.out.println("\nType a number and press Enter to continue.");
        System.out.print("Your choice: ");

        String userChoice = inputScanner.nextLine().trim();

        switch (taskType) {
            case "deposit":
                if (userChoice.equals("1")) {
                    addDeposit();
                } else if (userChoice.equals("2")) {
                    return;
                }
                break;

            case "payment":
                if (userChoice.equals("1")) {
                    makePayment();
                } else if (userChoice.equals("2")) {
                    return;
                }
                break;

            case "report":
                if (userChoice.equals("1")) {
                    displayReportsScreen();
                } else if (userChoice.equals("2")) {
                    return;
                } else if (userChoice.equals("3")) {
                    displayLedgerScreen();
                } else if (userChoice.equals("4")) {
                    displayHomeScreen();
                }
                break;

            case "search":
                if (userChoice.equals("1")) {
                    searchByVendor();
                } else if (userChoice.equals("2")) {
                    displayReportsScreen();
                } else if (userChoice.equals("3")) {
                    displayLedgerScreen();
                } else if (userChoice.equals("4")) {
                    displayHomeScreen();
                }
                break;

            case "ledger":
                if (userChoice.equals("1")) {
                    return;
                } else if (userChoice.equals("2")) {
                    displayHomeScreen();
                }
                break;

            default:
                break;
        }
    }
}