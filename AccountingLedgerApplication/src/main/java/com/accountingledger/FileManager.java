package com.accountingledger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileManager {
    private static final String dataFilePath = "src/main/resources/transactions.csv";
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void saveTransaction(Transaction transaction) {
        File dataFile = new File(dataFilePath);

        try {
            File folderPath = dataFile.getParentFile();
            if (!folderPath.exists()) {
                folderPath.mkdirs();
            }

            boolean fileExists = dataFile.exists();
            boolean isEmpty = !fileExists || dataFile.length() == 0;

            FileWriter fileWriter = new FileWriter(dataFile, true);

            if (isEmpty) {
                fileWriter.write("date|time|description|vendor|amount\n");
            }

            fileWriter.write(transaction.toString() + "\n");

            fileWriter.close();

            System.out.println("Transaction saved successfully!");

        } catch (IOException ioException) {
            System.out.println("Something went wrong while saving the transaction.");
            ioException.printStackTrace();
        }
    }

    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactionsList = new ArrayList<>();
        File dataFile = new File(dataFilePath);

        try {
            // to check if the file exists
            if (!dataFile.exists()) {
                return transactionsList;  // Return empty list if file doesn't exist
            }
            BufferedReader fileReader = new BufferedReader(new FileReader(dataFile));

            // Skip the header line
            fileReader.readLine();

            String dataLine;
            while ((dataLine = fileReader.readLine()) != null) {
                try {
                    String[] dataParts = dataLine.split("\\|");
                    if (dataParts.length == 5) {
                        LocalDate transactionDate = LocalDate.parse(dataParts[0]);
                        LocalTime transactionTime = LocalTime.parse(dataParts[1]);
                        String transactionDescription = dataParts[2];
                        String vendorName = dataParts[3];
                        double transactionAmount = Double.parseDouble(dataParts[4]);

                        Transaction transaction = new Transaction(transactionDate, transactionTime,
                                transactionDescription, vendorName, transactionAmount);
                        transactionsList.add(transaction);
                    }
                } catch (Exception parseException) {
                    System.out.println("Error parsing transaction: " + dataLine);
                }
            }
            fileReader.close();

        } catch (IOException ioException) {
            System.out.println("Error reading transactions file.");
            ioException.printStackTrace();
        }

        return transactionsList;
    }
}