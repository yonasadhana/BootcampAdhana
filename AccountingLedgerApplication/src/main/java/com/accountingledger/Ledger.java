package com.accountingledger;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ledger {
    private ArrayList<Transaction> transactionsList; // This class handles all our transaction data and makes it easy to work with
    // Think of it like a filing cabinet that keeps all our money records organized
    // This stores all our transactions in one place
    public Ledger() {
        this.transactionsList = FileManager.loadTransactions();  // When we create a new Ledger, it automatically loads saved transactions
    }

    // PART 1: BASIC TRANSACTION HANDLING
    // These methods handle the core stuff - getting transactions and adding new ones

    // Just gives us back all transactions we have
    public ArrayList<Transaction> getAllTransactions() {
        return transactionsList;
    }

    // This filters out just the money coming in (positive amounts)
    public ArrayList<Transaction> getDeposits() {
        ArrayList<Transaction> depositsList = new ArrayList<>();

        for (Transaction transactionItem : transactionsList) {
            if (transactionItem.getAmount() > 0) {
                depositsList.add(transactionItem);
            }
        }

        return depositsList;
    }

    // This filters out just the money going out (negative amounts)
    public ArrayList<Transaction> getPayments() {
        ArrayList<Transaction> paymentsList = new ArrayList<>();

        for (Transaction transactionItem : transactionsList) {
            if (transactionItem.getAmount() < 0) {
                paymentsList.add(transactionItem);
            }
        }

        return paymentsList;
    }

    // This adds a new transaction to our list and saves it to the file
    public void addTransaction(Transaction transactionItem) {
        transactionsList.add(transactionItem);
        FileManager.saveTransaction(transactionItem);
    }

    // PART 2: TIME-BASED FILTERING
    // These methods let us look at transactions from specific time periods
    // Like showing bank statements for different months or years

    // Gets transactions from the start of this month until today
    public ArrayList<Transaction> getMonthToDateTransactions() {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate monthStartDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        for (Transaction transactionItem : transactionsList) {
            LocalDate transactionDate = transactionItem.getDate();

            boolean isAfterStartDate = transactionDate.equals(monthStartDate) || transactionDate.isAfter(monthStartDate);
            boolean isBeforeEndDate = transactionDate.equals(currentDate) || transactionDate.isBefore(currentDate);

            if (isAfterStartDate && isBeforeEndDate) {
                filteredTransactions.add(transactionItem);
            }
        }

        return filteredTransactions;
    }

    // Gets all transactions from last month
    public ArrayList<Transaction> getPreviousMonthTransactions() {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        int previousMonth = currentDate.getMonthValue() - 1;
        int yearValue = currentDate.getYear();

        // Handle if we're in January and need to go back to December of last year
        if (previousMonth < 1) {
            previousMonth = 12;
            yearValue = yearValue - 1;
        }

        LocalDate startDate = LocalDate.of(yearValue, previousMonth, 1);

        int lastDayOfMonth = startDate.lengthOfMonth();
        LocalDate endDate = LocalDate.of(yearValue, previousMonth, lastDayOfMonth);

        for (Transaction transactionItem : transactionsList) {
            LocalDate transactionDate = transactionItem.getDate();

            boolean isAfterStartDate = transactionDate.equals(startDate) || transactionDate.isAfter(startDate);
            boolean isBeforeEndDate = transactionDate.equals(endDate) || transactionDate.isBefore(endDate);

            if (isAfterStartDate && isBeforeEndDate) {
                filteredTransactions.add(transactionItem);
            }
        }

        return filteredTransactions;
    }

    // Gets transactions from January 1st of this year until today
    public ArrayList<Transaction> getYearToDateTransactions() {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        LocalDate yearStartDate = LocalDate.of(currentDate.getYear(), 1, 1);

        for (Transaction transactionItem : transactionsList) {
            LocalDate transactionDate = transactionItem.getDate();

            boolean isAfterStartDate = transactionDate.equals(yearStartDate) || transactionDate.isAfter(yearStartDate);
            boolean isBeforeEndDate = transactionDate.equals(currentDate) || transactionDate.isBefore(currentDate);

            if (isAfterStartDate && isBeforeEndDate) {
                filteredTransactions.add(transactionItem);
            }
        }

        return filteredTransactions;
    }

    // PART 3: FILTERING AND CALCULATIONS
    // These methods do the fancy stuff like searching and calculating totals
    // This is where we go beyond basic lists to more analysis-type things

    // Gets all transactions from last year
    public ArrayList<Transaction> getPreviousYearTransactions() {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        int previousYear = LocalDate.now().getYear() - 1;
        LocalDate startDate = LocalDate.of(previousYear, 1, 1);
        LocalDate endDate = LocalDate.of(previousYear, 12, 31);

        for (Transaction transactionItem : transactionsList) {
            LocalDate transactionDate = transactionItem.getDate();

            boolean isAfterStartDate = transactionDate.equals(startDate) || transactionDate.isAfter(startDate);
            boolean isBeforeEndDate = transactionDate.equals(endDate) || transactionDate.isBefore(endDate);

            if (isAfterStartDate && isBeforeEndDate) {
                filteredTransactions.add(transactionItem);
            }
        }

        return filteredTransactions;
    }

    // Lets you search for transactions with a specific vendor
    // Like finding all your Amazon purchases or coffee shop visits
    public ArrayList<Transaction> searchByVendor(String searchVendorName) {
        ArrayList<Transaction> searchResults = new ArrayList<>();

        // Don't bother searching if they didn't enter anything
        if (searchVendorName == null || searchVendorName.trim().equals("")) {
            return searchResults;
        }

        // Make the search work regardless of uppercase/lowercase letters
        String searchTerm = searchVendorName.trim().toLowerCase();

        for (Transaction transactionItem : transactionsList) {
            String vendorName = transactionItem.getVendor().toLowerCase();

            // Check if this transaction's vendor contains what we're looking for
            if (vendorName.contains(searchTerm)) {
                searchResults.add(transactionItem);
            }
        }

        return searchResults;
    }

    // Adds up the total amount for any list of transactions
    // Useful for seeing your overall balance or spending in a category
    public double calculateTotal(ArrayList<Transaction> transactionList) {
        double totalAmount = 0;

        for (Transaction transactionItem : transactionList) {
            totalAmount += transactionItem.getAmount();
        }

        return totalAmount;
    }
}