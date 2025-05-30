package com.delicious.pos.utils;

import com.delicious.pos.models.Receipt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReceiptWriter {
    private static final String RECEIPTS_DIRECTORY = "C:\\Bootcamp\\BootcampAdhana\\DELIciousSandwiches\\src\\main\\resources\\receipts";

    public ReceiptWriter() {
        createReceiptsDirectory();
    }

    private void createReceiptsDirectory() {
        File directory = new File(RECEIPTS_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void writeReceipt(Receipt receipt) throws IOException {
        String filePath = RECEIPTS_DIRECTORY + File.separator + receipt.getFileName();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(receipt.getContent());
            writer.flush();
        }
    }
}