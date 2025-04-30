package com.accountingledger;

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
        System.out.println("\n******************* Home Screen *************");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.println("Press X to exit the application.");


        while (true) {
            System.out.print("Please select an option: ");
            String choice = scanner.nextLine();
            if (choice != null && choice.toUpperCase().equals("X")) {
                System.out.println("Thank you for using the Accounting Ledger Application. Goodbye!");
                break;
            } else {
                System.out.println("Please press X to exit.");
            }
        }
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
}