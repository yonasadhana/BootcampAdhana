package com.accountingledger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FileManager {
    private static final String filePath = "src/main/resources/transactions.csv";

    public static void saveTransaction(Transaction transaction) {
        File file = new File(filePath);

        try {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            boolean fileExists = file.exists();
            boolean isEmpty = !fileExists || file.length() == 0;

            FileWriter writer = new FileWriter(file, true);

            if (isEmpty) {
                writer.write("date|time|description|vendor|amount\n");
            }

            writer.write(transaction.toString() + "\n");

            writer.close();

            System.out.println("Transaction saved successfully!");

        } catch (IOException e) {
            System.out.println("Something went wrong while saving the transaction.");
            e.printStackTrace();
        }
    }

    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        File file = new File(filePath);

        try {
            // to check if the file exists
            if (!file.exists()) {
                return transactions;  // Return empty list if file doesn't exist
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length == 5) {
                        LocalDate date = LocalDate.parse(parts[0]);
                        LocalTime time = LocalTime.parse(parts[1]);
                        String description = parts[2];
                        String vendor = parts[3];
                        double amount = Double.parseDouble(parts[4]);

                        Transaction transaction = new Transaction(date, time, description, vendor, amount);
                        transactions.add(transaction);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing transaction: " + line);
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading transactions file.");
            e.printStackTrace();
        }

        return transactions;
    }
}