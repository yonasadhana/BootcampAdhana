package com.accountingledger;

import java.util.ArrayList;

public class Ledger {
    // Store all transactions
    private ArrayList<Transaction> transactions;

    // Constructor to load transactions
    public Ledger() {
        // Load transactions from file when Ledger is created
        this.transactions = FileManager.loadTransactions();
    }

    // to get all transactions
    public ArrayList<Transaction> getAllTransactions() {
        return transactions;
    }

    // to get only deposit transactions
    public ArrayList<Transaction> getDeposits() {
        ArrayList<Transaction> deposits = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                deposits.add(transaction);
            }
        }

        return deposits;
    }

    // to geet only payment transactions
    public ArrayList<Transaction> getPayments() {
        ArrayList<Transaction> payments = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                payments.add(transaction);
            }
        }

        return payments;
    }

    //to add new transaction
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        FileManager.saveTransaction(transaction);

    }
}