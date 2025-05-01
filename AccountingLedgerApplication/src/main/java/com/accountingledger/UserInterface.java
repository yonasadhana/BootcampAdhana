package com.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Scanner inputScanner = new Scanner(System.in);
    private Ledger transactionLedger;

    public UserInterface(Ledger transactionLedger) {
        this.transactionLedger = transactionLedger;
    }

    // Part 2: Home Screen and Transaction Entry
    public void displayHomeScreen() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n*********** Home Screen ***********");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Please select an option: ");

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
                        isRunning = false;
                        System.out.println("Thank you for using the Accounting Ledger Application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Please enter a selection.");
            }
        }
    }

    private void addDeposit() {
        try {
            System.out.println("\n*********** Add Deposit ***********");

            System.out.print("Description: ");
            String transactionDescription = inputScanner.nextLine();

            System.out.print("Vendor: ");
            String vendorName = inputScanner.nextLine();

            System.out.print("Amount: $");
            double transactionAmount = Double.parseDouble(inputScanner.nextLine());

            if (transactionAmount <= 0) {
                System.out.println("Deposit amount must be positive. Transaction cancelled.");
                showPostTaskOptions("deposit");
                return;
            }

            Transaction depositTransaction = new Transaction(LocalDate.now(), LocalTime.now(),
                    transactionDescription, vendorName, transactionAmount);
            transactionLedger.addTransaction(depositTransaction);

            System.out.println("Deposit added successfully!");
            showPostTaskOptions("deposit");

        } catch (NumberFormatException formatException) {
            System.out.println("Invalid amount format. Please enter a valid number.");
            showPostTaskOptions("deposit");
        } catch (Exception generalException) {
            System.out.println("Error adding deposit: " + generalException.getMessage());
            showPostTaskOptions("deposit");
        }
    }

    private void makePayment() {
        try {
            System.out.println("\n*********** Make Payment ***********");

            System.out.print("Description: ");
            String transactionDescription = inputScanner.nextLine();

            System.out.print("Vendor: ");
            String vendorName = inputScanner.nextLine();

            System.out.print("Amount: $");
            double transactionAmount = Double.parseDouble(inputScanner.nextLine());

            if (transactionAmount <= 0) {
                System.out.println("Payment amount must be positive. Transaction cancelled.");
                showPostTaskOptions("payment");
                return;
            }

            transactionAmount = -transactionAmount;

            Transaction paymentTransaction = new Transaction(LocalDate.now(), LocalTime.now(),
                    transactionDescription, vendorName, transactionAmount);
            transactionLedger.addTransaction(paymentTransaction);

            System.out.println("Payment recorded successfully!");
            showPostTaskOptions("payment");

        } catch (NumberFormatException formatException) {
            System.out.println("Invalid amount format. Please enter a valid number.");
            showPostTaskOptions("payment");
        } catch (Exception generalException) {
            System.out.println("Error making payment: " + generalException.getMessage());
            showPostTaskOptions("payment");
        }
    }

    // Part 3: Ledger Display Functions
    private void displayLedgerScreen() {
        boolean isViewingLedger = true;

        while (isViewingLedger) {
            System.out.println("\n*********** Ledger ***********");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Please select an option: ");

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
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Please enter a selection.");
            }
        }
    }

    private void displayAllEntries() {
        System.out.println("\n*********** All Entries ***********");
        ArrayList<Transaction> transactionsList = transactionLedger.getAllTransactions();

        if (transactionsList.isEmpty()) {
            System.out.println("No transactions found.");
            showPostTaskOptions("ledger");
            return;
        }

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactionsList.size() - 1; i >= 0; i--) {
            Transaction transactionItem = transactionsList.get(i);
            System.out.printf("%-10s | %-8s | %-28s | %-22s | $%+9.2f%n",
                    transactionItem.getDate(),
                    transactionItem.getTime().toString().substring(0, 8),
                    limitString(transactionItem.getDescription(), 28),
                    limitString(transactionItem.getVendor(), 22),
                    transactionItem.getAmount());
        }

        showPostTaskOptions("ledger");
    }

    private void displayDeposits() {
        System.out.println("\n*********** Deposits ***********");
        ArrayList<Transaction> depositsList = transactionLedger.getDeposits();

        if (depositsList.isEmpty()) {
            System.out.println("No deposits found.");
            showPostTaskOptions("ledger");
            return;
        }

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = depositsList.size() - 1; i >= 0; i--) {
            Transaction transactionItem = depositsList.get(i);
            System.out.printf("%-10s | %-8s | %-28s | %-22s | $%9.2f%n",
                    transactionItem.getDate(),
                    transactionItem.getTime().toString().substring(0, 8),
                    limitString(transactionItem.getDescription(), 28),
                    limitString(transactionItem.getVendor(), 22),
                    transactionItem.getAmount());
        }

        showPostTaskOptions("ledger");
    }

    private void displayPayments() {
        System.out.println("\n*********** Payments ***********");
        ArrayList<Transaction> paymentsList = transactionLedger.getPayments();

        if (paymentsList.isEmpty()) {
            System.out.println("No payments found.");
            showPostTaskOptions("ledger");
            return;
        }

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = paymentsList.size() - 1; i >= 0; i--) {
            Transaction transactionItem = paymentsList.get(i);
            System.out.printf("%-10s | %-8s | %-28s | %-22s | $%9.2f%n",
                    transactionItem.getDate(),
                    transactionItem.getTime().toString().substring(0, 8),
                    limitString(transactionItem.getDescription(), 28),
                    limitString(transactionItem.getVendor(), 22),
                    transactionItem.getAmount());
        }

        showPostTaskOptions("ledger");
    }

    // Part 4: Reports and Transaction Filtering
    private void displayReportsScreen() {
        boolean isViewingReports = true;

        while (isViewingReports) {
            System.out.println("\n*********** Reports ***********");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Please select an option: ");

            String userChoice = inputScanner.nextLine();

            if (userChoice == null || userChoice.isEmpty()) {
                System.out.println("Please enter a selection.");
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
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Methods for report generation
    private void reportMonthToDate() {
        System.out.println("\n*********** Month To Date Report ***********");
        LocalDate currentDate = LocalDate.now();
        LocalDate monthStartDate = currentDate.withDayOfMonth(1);
        System.out.println("Period: " + monthStartDate + " to " + currentDate);

        ArrayList<Transaction> filteredTransactions = transactionLedger.getMonthToDateTransactions();
        double totalAmount = transactionLedger.calculateTotal(filteredTransactions);

        displayTransactionsWithTotal(filteredTransactions, totalAmount);
        showPostTaskOptions("report");
    }

    private void reportPreviousMonth() {
        YearMonth previousMonth = YearMonth.from(LocalDate.now()).minusMonths(1);

        System.out.println("\n*********** Previous Month Report ***********");
        System.out.println("Period: " + previousMonth.atDay(1) + " to " + previousMonth.atEndOfMonth());

        ArrayList<Transaction> filteredTransactions = transactionLedger.getPreviousMonthTransactions();
        double totalAmount = transactionLedger.calculateTotal(filteredTransactions);

        displayTransactionsWithTotal(filteredTransactions, totalAmount);
        showPostTaskOptions("report");
    }

    private void reportYearToDate() {
        System.out.println("\n*********** Year To Date Report ***********");
        LocalDate currentDate = LocalDate.now();
        LocalDate yearStartDate = currentDate.withDayOfYear(1);
        System.out.println("Period: " + yearStartDate + " to " + currentDate);

        ArrayList<Transaction> filteredTransactions = transactionLedger.getYearToDateTransactions();
        double totalAmount = transactionLedger.calculateTotal(filteredTransactions);

        displayTransactionsWithTotal(filteredTransactions, totalAmount);
        showPostTaskOptions("report");
    }

    private void reportPreviousYear() {
        int previousYear = LocalDate.now().getYear() - 1;

        System.out.println("\n*********** Previous Year Report ***********");
        System.out.println("Period: " + LocalDate.of(previousYear, 1, 1) + " to " + LocalDate.of(previousYear, 12, 31));

        ArrayList<Transaction> filteredTransactions = transactionLedger.getPreviousYearTransactions();
        double totalAmount = transactionLedger.calculateTotal(filteredTransactions);

        displayTransactionsWithTotal(filteredTransactions, totalAmount);
        showPostTaskOptions("report");
    }

    private void searchByVendor() {
        System.out.println("\n*********** Vendor Search ***********");

        System.out.print("Enter vendor name to search: ");
        String searchVendorName = inputScanner.nextLine().trim();

        if (searchVendorName.isEmpty()) {
            System.out.println("Search cancelled. Please enter a vendor name.");
            showPostTaskOptions("search");
            return;
        }

        System.out.println("\n*********** Search Results for: " + searchVendorName + " ***********");

        ArrayList<Transaction> searchResults = transactionLedger.searchByVendor(searchVendorName);
        double totalAmount = transactionLedger.calculateTotal(searchResults);

        displayTransactionsWithTotal(searchResults, totalAmount);
        showPostTaskOptions("search");
    }

    /**
     * Utility method to display filtered transactions with totals
     * Had to write this to avoid copying the same display code everywhere
     * Also calculates totals and gives a summary of whether you're making or losing money
     */
    private void displayTransactionsWithTotal(ArrayList<Transaction> transactionsList, double totalAmount) {
        if (transactionsList.isEmpty()) {
            System.out.println("No matching transactions found.");
            // Don't show post-task options here since each calling method will do that
            return;
        }

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactionsList.size() - 1; i >= 0; i--) {
            Transaction transactionItem = transactionsList.get(i);
            System.out.printf("%-10s | %-8s | %-28s | %-22s | $%+9.2f%n",
                    transactionItem.getDate(),
                    transactionItem.getTime().toString().substring(0, 8),
                    limitString(transactionItem.getDescription(), 28),
                    limitString(transactionItem.getVendor(), 22),
                    transactionItem.getAmount());
        }

        System.out.println("-----------+----------+------------------------------+------------------------+------------");
        System.out.printf("%52s | $%+9.2f%n", "Total", totalAmount);

        if (totalAmount > 0) {
            System.out.println("Summary: Net income is positive.");
        } else if (totalAmount < 0) {
            System.out.println("Summary: Net income is negative.");
        } else {
            System.out.println("Summary: Balanced budget.");
        }
    }

    // Utility method to limit string length for display
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

    // New method to display options after completing a task
    private void showPostTaskOptions(String taskType) {
        System.out.println("\n*********** What would you like to do next? ***********");

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

        System.out.print("Enter your choice: ");
        String userChoice = inputScanner.nextLine().trim();

        switch (taskType) {
            case "deposit":
                if (userChoice.equals("1")) {
                    addDeposit();
                }
                // For option 2 or any other input, just return to calling method which will go back to home
                break;

            case "payment":
                if (userChoice.equals("1")) {
                    makePayment();
                }
                // For option 2 or any other input, just return to calling method which will go back to home
                break;

            case "report":
                if (userChoice.equals("1")) {
                    displayReportsScreen();
                } else if (userChoice.equals("2")) {
                    // Just return to reports menu
                } else if (userChoice.equals("3")) {
                    displayLedgerScreen();
                }
                // For option 4 or any other input, just return to calling method which will go back to home
                break;

            case "search":
                if (userChoice.equals("1")) {
                    searchByVendor();
                } else if (userChoice.equals("2")) {
                    displayReportsScreen();
                } else if (userChoice.equals("3")) {
                    displayLedgerScreen();
                }
                // For option 4 or any other input, just return to calling method which will go back to home
                break;

            case "ledger":
                if (userChoice.equals("1")) {
                    // Just return to ledger menu
                }
                // For option 2 or any other input, just return to calling method which will go back to home
                break;

            default:
                // Just continue with default flow
        }
    }
}