package com.accountingledger;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ledger {
    private ArrayList<Transaction> transactions; // This class handles all our transaction data and makes it easy to work with
    // Think of it like a filing cabinet that keeps all our money records organized
    // This stores all our transactions in one place
    public Ledger() {
        this.transactions = FileManager.loadTransactions();  // When we create a new Ledger, it automatically loads saved transactions
    }

    // PART 1: BASIC TRANSACTION HANDLING
    // These methods handle the core stuff - getting transactions and adding new ones

    // Just gives us back all transactions we have
    public ArrayList<Transaction> getAllTransactions() {
        return transactions;
    }

    // This filters out just the money coming in (positive amounts)
    public ArrayList<Transaction> getDeposits() {
        ArrayList<Transaction> deposits = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                deposits.add(transaction);
            }
        }

        return deposits;
    }

    // This filters out just the money going out (negative amounts)
    public ArrayList<Transaction> getPayments() {
        ArrayList<Transaction> payments = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                payments.add(transaction);
            }
        }

        return payments;
    }

    // This adds a new transaction to our list and saves it to the file
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        FileManager.saveTransaction(transaction);
    }

    // PART 2: TIME-BASED FILTERING
    // These methods let us look at transactions from specific time periods
    // Like showing bank statements for different months or years

    // Gets transactions from the start of this month until today
    public ArrayList<Transaction> getMonthToDateTransactions() {
        ArrayList<Transaction> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(today.getYear(), today.getMonth(), 1);

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();

            boolean isAfterStartDate = transactionDate.equals(firstDayOfMonth) || transactionDate.isAfter(firstDayOfMonth);
            boolean isBeforeEndDate = transactionDate.equals(today) || transactionDate.isBefore(today);

            if (isAfterStartDate && isBeforeEndDate) {
                result.add(transaction);
            }
        }

        return result;
    }

    // Gets all transactions from last month
    public ArrayList<Transaction> getPreviousMonthTransactions() {
        ArrayList<Transaction> result = new ArrayList<>();

        LocalDate today = LocalDate.now();

        int prevMonth = today.getMonthValue() - 1;
        int year = today.getYear();

        // Handle if we're in January and need to go back to December of last year
        if (prevMonth < 1) {
            prevMonth = 12;
            year = year - 1;
        }

        LocalDate firstDay = LocalDate.of(year, prevMonth, 1);

        int lastDayOfMonth = firstDay.lengthOfMonth();
        LocalDate lastDay = LocalDate.of(year, prevMonth, lastDayOfMonth);

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();

            boolean isAfterStartDate = transactionDate.equals(firstDay) || transactionDate.isAfter(firstDay);
            boolean isBeforeEndDate = transactionDate.equals(lastDay) || transactionDate.isBefore(lastDay);

            if (isAfterStartDate && isBeforeEndDate) {
                result.add(transaction);
            }
        }

        return result;
    }

    // Gets transactions from January 1st of this year until today
    public ArrayList<Transaction> getYearToDateTransactions() {
        ArrayList<Transaction> result = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = LocalDate.of(today.getYear(), 1, 1);

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();

            boolean isAfterStartDate = transactionDate.equals(firstDayOfYear) || transactionDate.isAfter(firstDayOfYear);
            boolean isBeforeEndDate = transactionDate.equals(today) || transactionDate.isBefore(today);

            if (isAfterStartDate && isBeforeEndDate) {
                result.add(transaction);
            }
        }

        return result;
    }

    // PART 3: FILTERING AND CALCULATIONS
    // These methods do the fancy stuff like searching and calculating totals
    // This is where we go beyond basic lists to more analysis-type things

    // Gets all transactions from last year
    public ArrayList<Transaction> getPreviousYearTransactions() {
        ArrayList<Transaction> result = new ArrayList<>();

        int previousYear = LocalDate.now().getYear() - 1;
        LocalDate firstDay = LocalDate.of(previousYear, 1, 1);
        LocalDate lastDay = LocalDate.of(previousYear, 12, 31);

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();

            boolean isAfterStartDate = transactionDate.equals(firstDay) || transactionDate.isAfter(firstDay);
            boolean isBeforeEndDate = transactionDate.equals(lastDay) || transactionDate.isBefore(lastDay);

            if (isAfterStartDate && isBeforeEndDate) {
                result.add(transaction);
            }
        }

        return result;
    }

    // Lets you search for transactions with a specific vendor
    // Like finding all your Amazon purchases or coffee shop visits
    public ArrayList<Transaction> searchByVendor(String vendorName) {
        ArrayList<Transaction> result = new ArrayList<>();

        // Don't bother searching if they didn't enter anything
        if (vendorName == null || vendorName.trim().equals("")) {
            return result;
        }

        // Make the search work regardless of uppercase/lowercase letters
        String searchTerm = vendorName.trim().toLowerCase();

        for (Transaction transaction : transactions) {
            String vendor = transaction.getVendor().toLowerCase();

            // Check if this transaction's vendor contains what we're looking for
            if (vendor.contains(searchTerm)) {
                result.add(transaction);
            }
        }

        return result;
    }

    // Adds up the total amount for any list of transactions
    // Useful for seeing your overall balance or spending in a category
    public double calculateTotal(ArrayList<Transaction> transactionList) {
        double total = 0;

        for (Transaction transaction : transactionList) {
            total += transaction.getAmount();
        }

        return total;
    }
}