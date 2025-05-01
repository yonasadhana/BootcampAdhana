package com.accountingledger;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Accounting Ledger Application!");  // Part 1: Basic Structure/Main Method

        // Create ledger and load transactions
        Ledger ledger = new Ledger();
        System.out.println("Transactions loaded: " + ledger.getAllTransactions().size());

        // Create user interface and start the application
        UserInterface ui = new UserInterface(ledger);
        ui.displayHomeScreen();
    }
}