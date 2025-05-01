package com.accountingledger;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Accounting Ledger Application!"); // Part 1: Basic Structure/Main Method

        // Create ledger and load transactions
        Ledger transactionLedger = new Ledger();
        System.out.println("Transactions loaded: " + transactionLedger.getAllTransactions().size());

        // Create user interface and start the application
        UserInterface userInterface = new UserInterface(transactionLedger);
        userInterface.displayHomeScreen();
    }
}