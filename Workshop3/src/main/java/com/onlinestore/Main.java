package com.onlinestore;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Online Store!");
        System.out.println("Loading products...");

        List<Product> products = FileLoader.readFile();

        if (products.isEmpty()) {
            System.out.println("No products were loaded. Exiting program.");
            return;
        }

        System.out.println(products.size() + " products loaded successfully.");

        UserInterface ui = new UserInterface(products);
        ui.displayHomeScreen();
    }
}