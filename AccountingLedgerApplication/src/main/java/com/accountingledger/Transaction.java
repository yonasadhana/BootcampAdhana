package com.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate transactionDate;
    private LocalTime transactionTime;
    private String transactionDescription;
    private String vendorName;
    private double transactionAmount;
    // Create a formatter to display time without nanoseconds
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Transaction(LocalDate transactionDate, LocalTime transactionTime, String transactionDescription, String vendorName, double transactionAmount) {
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.transactionDescription = transactionDescription;
        this.vendorName = vendorName;
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getDate() {
        return transactionDate;
    }

    public LocalTime getTime() {
        return transactionTime;
    }

    public String getDescription() {
        return transactionDescription;
    }

    public String getVendor() {
        return vendorName;
    }

    public double getAmount() {
        return transactionAmount;
    }

    @Override
    public String toString() {
        // Format time using the formatter to get HH:mm:ss
        String formattedTime = transactionTime.format(timeFormatter);
        return String.format("%s|%s|%s|%s|%.2f", transactionDate.toString(), formattedTime, transactionDescription, vendorName, transactionAmount);
    }
}