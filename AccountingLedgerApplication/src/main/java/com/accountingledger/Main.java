package com.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;

// Part 1: Basic Structure/Main Method
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Transaction> transactions;

    public static void main(String[] args) {
        System.out.println("Welcome to the Accounting Ledger Application!");
        loadTransactionsData();
        displayHomeScreen();
    }

    private static void loadTransactionsData() {
        transactions = FileManager.loadTransactions();
        System.out.println("Transactions loaded: " + transactions.size());
    }

    private static String limitString(String str, int maxLength) {
        if (str == null) {
            return "";
        }

        if (str.length() <= maxLength) {
            return str;
        } else {
            return str.substring(0, maxLength - 3) + "...";
        }
    }

    private interface TransactionFilter {
        boolean match(Transaction transaction);
    }

    // Part 2: Home Screen and Transaction Entry
    private static void displayHomeScreen() {
        boolean running = true;

        while (running) {
            System.out.println("\n*********** Home Screen ***********");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Please select an option: ");

            String choice = scanner.nextLine();

            if (choice != null && !choice.isEmpty()) {
                switch (choice.toUpperCase()) {
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
                        running = false;
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

    private static void addDeposit() {
        try {
            System.out.println("\n*********** Add Deposit ***********");

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Vendor: ");
            String vendor = scanner.nextLine();

            System.out.print("Amount: $");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0) {
                System.out.println("Deposit amount must be positive. Transaction cancelled.");
                return;
            }

            Transaction deposit = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            FileManager.saveTransaction(deposit);

            transactions.add(deposit);

            System.out.println("Deposit added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error adding deposit: " + e.getMessage());
        }
    }

    private static void makePayment() {
        try {
            System.out.println("\n*********** Make Payment ***********");

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Vendor: ");
            String vendor = scanner.nextLine();

            System.out.print("Amount: $");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0) {
                System.out.println("Payment amount must be positive. Transaction cancelled.");
                return;
            }

            amount = -amount;

            Transaction payment = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            FileManager.saveTransaction(payment);

            transactions.add(payment);

            System.out.println("Payment recorded successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error making payment: " + e.getMessage());
        }
    }

    // Part 3: Ledger Display Functions
    private static void displayLedgerScreen() {
        boolean viewingLedger = true;

        while (viewingLedger) {
            System.out.println("\n*********** Ledger ***********");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Please select an option: ");

            String choice = scanner.nextLine();

            if (choice != null && !choice.isEmpty()) {
                switch (choice.toUpperCase()) {
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
                        viewingLedger = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Please enter a selection.");
            }
        }
    }

    private static void displayAllEntries() {
        System.out.println("\n*********** All Entries ***********");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            System.out.printf("%-10s | %-8s | %-28s | %-22s | $%+9.2f%n", t.getDate(), t.getTime().toString().substring(0, 8), limitString(t.getDescription(), 28), limitString(t.getVendor(), 22), t.getAmount());
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void displayDeposits() {
        System.out.println("\n*********** Deposits ***********");
        boolean found = false;

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() > 0) {
                found = true;
                System.out.printf("%-10s | %-8s | %-28s | %-22s | $%9.2f%n", t.getDate(), t.getTime().toString().substring(0, 8), limitString(t.getDescription(), 28), limitString(t.getVendor(), 22), t.getAmount());
            }
        }

        if (!found) {
            System.out.println("No deposits found.");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void displayPayments() {
        System.out.println("\n*********** Payments ***********");
        boolean found = false;

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0) {
                found = true;
                System.out.printf("%-10s | %-8s | %-28s | %-22s | $%9.2f%n", t.getDate(), t.getTime().toString().substring(0, 8), limitString(t.getDescription(), 28), limitString(t.getVendor(), 22), t.getAmount());
            }
        }

        if (!found) {
            System.out.println("No payments found.");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    // Part 4: Reports and Transaction Filtering
    private static void displayReportsScreen() {
        boolean viewingReports = true;

        while (viewingReports) {
            System.out.println("\n*********** Reports ***********");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Please select an option: ");

            String choice = scanner.nextLine();

            if (choice == null || choice.isEmpty()) {
                System.out.println("Please enter a selection.");
                continue;
            }

            choice = choice.trim();

            switch (choice) {
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
                    viewingReports = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Shows transactions from the start of current month to today
     * This is super useful for monthly budgeting - helps me track how I'm doing so far this month
     * before all the bills come in at the end
     */
    private static void reportMonthToDate() {
        System.out.println("\n*********** Month To Date Report ***********");
        System.out.println("Period: " + LocalDate.now().withDayOfMonth(1) + " to " + LocalDate.now());

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        displayFilteredTransactions(t -> (t.getDate().isEqual(firstDayOfMonth) || t.getDate().isAfter(firstDayOfMonth)) && (t.getDate().isEqual(today) || t.getDate().isBefore(today)));
    }

    /**
     * Shows transactions from previous month
     * Great for reviewing last month's spending and planning for the current month
     * I use this one all the time to compare my recurring expenses
     */
    private static void reportPreviousMonth() {
        YearMonth previousMonth = YearMonth.from(LocalDate.now()).minusMonths(1);

        System.out.println("\n*********** Previous Month Report ***********");
        System.out.println("Period: " + previousMonth.atDay(1) + " to " + previousMonth.atEndOfMonth());

        LocalDate firstDayOfPreviousMonth = previousMonth.atDay(1);
        LocalDate lastDayOfPreviousMonth = previousMonth.atEndOfMonth();

        displayFilteredTransactions(t -> (t.getDate().isEqual(firstDayOfPreviousMonth) || t.getDate().isAfter(firstDayOfPreviousMonth)) && (t.getDate().isEqual(lastDayOfPreviousMonth) || t.getDate().isBefore(lastDayOfPreviousMonth)));
    }

    /**
     * Shows all transactions from the beginning of the year till now
     * Really needed this for tax preparation - helps me see the bigger picture
     * of yearly spending vs income
     */
    private static void reportYearToDate() {
        System.out.println("\n*********** Year To Date Report ***********");
        System.out.println("Period: " + LocalDate.now().withDayOfYear(1) + " to " + LocalDate.now());

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = today.withDayOfYear(1);

        displayFilteredTransactions(t -> (t.getDate().isEqual(firstDayOfYear) || t.getDate().isAfter(firstDayOfYear)) && (t.getDate().isEqual(today) || t.getDate().isBefore(today)));
    }

    /**
     * Shows transactions from previous calendar year
     * Added this because my accountant kept asking for year-end reports
     * Makes tax filing way easier when everything's in one place
     */
    private static void reportPreviousYear() {
        int previousYear = LocalDate.now().getYear() - 1;

        System.out.println("\n*********** Previous Year Report ***********");
        System.out.println("Period: " + LocalDate.of(previousYear, 1, 1) + " to " + LocalDate.of(previousYear, 12, 31));

        LocalDate firstDayOfPreviousYear = LocalDate.of(previousYear, 1, 1);
        LocalDate lastDayOfPreviousYear = LocalDate.of(previousYear, 12, 31);

        displayFilteredTransactions(t -> (t.getDate().isEqual(firstDayOfPreviousYear) || t.getDate().isAfter(firstDayOfPreviousYear)) && (t.getDate().isEqual(lastDayOfPreviousYear) || t.getDate().isBefore(lastDayOfPreviousYear)));
    }

    /**
     * Searches transactions by vendor name (case insensitive)
     * I use this ALL THE TIME to find how much I've been spending at specific stores
     * or to track down that one transaction I can't remember the date for
     */
    private static void searchByVendor() {
        System.out.println("\n*********** Vendor Search ***********");

        System.out.print("Enter vendor name to search: ");
        String vendorSearch = scanner.nextLine().trim().toLowerCase();

        if (vendorSearch.isEmpty()) {
            System.out.println("Search cancelled. Please enter a vendor name.");
            return;
        }

        System.out.println("\n*********** Search Results for: " + vendorSearch + " ***********");

        displayFilteredTransactions(t -> t.getVendor().toLowerCase().contains(vendorSearch));
    }

    /**
     * Utility method to display filtered transactions with totals
     * Had to write this to avoid copying the same display code everywhere
     * Also calculates totals and gives a summary of whether you're making or losing money
     */
    private static void displayFilteredTransactions(TransactionFilter filter) {
        boolean found = false;
        double total = 0.0;

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (filter.match(t)) {
                found = true;
                total += t.getAmount();

                System.out.printf("%-10s | %-8s | %-28s | %-22s | $%+9.2f%n", t.getDate(), t.getTime().toString().substring(0, 8), limitString(t.getDescription(), 28), limitString(t.getVendor(), 22), t.getAmount());
            }
        }

        if (!found) {
            System.out.println("No matching transactions found.");
        } else {
            System.out.println("-----------+----------+------------------------------+------------------------+------------");
            System.out.printf("%52s | $%+9.2f%n", "Total", total);

            if (total > 0) {
                System.out.println("Summary: Net income is positive.");
            } else if (total < 0) {
                System.out.println("Summary: Net income is negative.");
            } else {
                System.out.println("Summary: Balanced budget.");
            }
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}