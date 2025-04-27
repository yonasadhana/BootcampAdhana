package com.onlinestore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    public static List<Product> readFile() {
        List<Product> productList = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("src/main/resources/products.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);


            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String sku = parts[0];
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]);
                String department = parts[3];

                Product product = new Product(sku, productName, price, department);
                productList.add(product);
            }

            bufferedReader.close();

        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }

        return productList;
    }
}
/* Use FileReader class and Buffered reader to load the file
Checked Exception coming up

Loop through the file line by line
Skip the first line of the file because it's the header

ake each line, and split it on the  |

we need to convert data as needed
Price will need a conversion to a double

Create a product object to hold the data

Put it the product in a list */