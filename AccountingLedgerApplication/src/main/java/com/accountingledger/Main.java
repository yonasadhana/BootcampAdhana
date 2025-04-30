package com.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

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

    private static void displayHomeScreen() {
        boolean running = true;

        while (running) {
            System.out.println("\n************* Home Screen ************");
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
            System.out.println("\n************** Add Deposit ************");

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
            System.out.println("\n==== Make Payment ====");

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

    private static void displayLedgerScreen() {
        boolean viewingLedger = true;

        while (viewingLedger) {
            System.out.println("\n*********** Ledger ******************");
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
        System.out.println("\n***************All Entries *****************");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            System.out.printf("%-10s | %-8s | %-28s | %-22s | $%+9.2f%n", t.getDate(), t.getTime().toString().substring(0, 8),
                    limitString(t.getDescription(), 28), limitString(t.getVendor(), 22), t.getAmount());
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void displayDeposits() {
        System.out.println("\n************ Deposits ***********");
        boolean found = false;

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() > 0) {
                found = true;
                System.out.printf("%-10s | %-8s | %-28s | %-22s | $%9.2f%n", t.getDate(), t.getTime().toString().substring(0, 8),
                        limitString(t.getDescription(), 28), limitString(t.getVendor(), 22), t.getAmount());
            }
        }

        if (!found) {
            System.out.println("No deposits found.");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void displayPayments() {
        System.out.println("\n********* Payments ***********");
        boolean found = false;

        System.out.println("Date       | Time     | Description                  | Vendor                 | Amount");
        System.out.println("-----------+----------+------------------------------+------------------------+------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0) {
                found = true;
                System.out.printf("%-10s | %-8s | %-28s | %-22s | $%9.2f%n", t.getDate(), t.getTime().toString().substring(0, 8),
                        limitString(t.getDescription(), 28), limitString(t.getVendor(), 22), t.getAmount());
            }
        }

        if (!found) {
            System.out.println("No payments found.");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void displayReportsScreen() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
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
}