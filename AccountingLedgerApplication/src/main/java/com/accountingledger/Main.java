package com.accountingledger;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        try {
            // Get the current date and time
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            // Example 1: Add a deposit
            String description1 = "Client payment";
            String vendor1 = "Jo's Consulting";
            double amount1 = 1500.00;

            Transaction transaction1 = new Transaction(date, time, description1, vendor1, amount1);
            FileManager.saveTransaction(transaction1);

            // Example 2: Add a payment
            String description2 = "Office supplies";
            String vendor2 = "UPS";
            double amount2 = -85.47;

            Transaction transaction2 = new Transaction(date, time, description2, vendor2, amount2);
            FileManager.saveTransaction(transaction2);

            // Example 3: Another deposit
            String description3 = "Monthly service fee";
            String vendor3 = "PB gas satiton";
            double amount3 = 275.00;

            Transaction transaction3 = new Transaction(date, time, description3, vendor3, amount3);
            FileManager.saveTransaction(transaction3);

            // Example 4: Another payment
            String description4 = "Software subscription";
            String vendor4 = "Adobe";
            double amount4 = -59.99;

            Transaction transaction4 = new Transaction(date, time, description4, vendor4, amount4);
            FileManager.saveTransaction(transaction4);

            // Example 5: One more deposit
            String description5 = "Product sale";
            String vendor5 = "best buy";
            double amount5 = 329.50;

            Transaction transaction5 = new Transaction(date, time, description5, vendor5, amount5);
            FileManager.saveTransaction(transaction5);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}