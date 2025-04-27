package com.onlinestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private List<Product> storeProducts;
    private ShoppingCart cart;
    private Scanner scanner;

    public UserInterface(List<Product> products) {
        this.storeProducts = products;
        this.cart = new ShoppingCart();
        this.scanner = new Scanner(System.in);
    }

    //main menu
    public void displayHomeScreen() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n******************** STORE HOME SCREEN ********************");
            System.out.println("1. Display Products  2. Display Cart  3. Exit");
            System.out.print("Enter your choice number from 1 to 3: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) displayProductsScreen();
            else if (choice == 2) displayCartScreen();
            else if (choice == 3) {
                System.out.println("Thank you for shopping with us!");
                exit = true;
            } else System.out.println("Invalid choice!");
        }
    }

    // products screen
    private void displayProductsScreen() {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("\n******************** PRODUCTS ********************");
            System.out.println("1. View All  2. Search by Name  3. Search by Price");
            System.out.println("4. Search by Department  5. Go Back");
            System.out.print("Enter your choice from 1 to 5: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showProducts(storeProducts);
                    addProductOption();
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine().toLowerCase();
                    List<Product> nameResults = new ArrayList<>();
                    for (Product p : storeProducts) {
                        if (p.getProductName().toLowerCase().contains(name)) {
                            nameResults.add(p);
                        }
                    }
                    showProducts(nameResults);
                    if (!nameResults.isEmpty()) addProductOption();
                    break;
                case 3:
                    System.out.print("Enter max price: $");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    List<Product> priceResults = new ArrayList<>();
                    for (Product p : storeProducts) {
                        if (p.getPrice() <= price) priceResults.add(p);
                    }
                    showProducts(priceResults);
                    if (!priceResults.isEmpty()) addProductOption();
                    break;
                case 4:
                    System.out.print("Enter department: ");
                    String dept = scanner.nextLine().toLowerCase();
                    List<Product> deptResults = new ArrayList<>();
                    for (Product p : storeProducts) {
                        if (p.getDepartment().toLowerCase().contains(dept)) {
                            deptResults.add(p);
                        }
                    }
                    showProducts(deptResults);
                    if (!deptResults.isEmpty()) addProductOption();
                    break;
                case 5:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // to show list of products
    private void showProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found!");
            return;
        }
        System.out.println("\nSKU - Name - Price - Department");
        System.out.println("--------------------------------");
        for (Product p : products) {
            System.out.println(p.getSku() + " - " + p.getProductName() + " - $" + p.getPrice() + " - " + p.getDepartment());
        }
    }

    //toadd product to cart option
    private void addProductOption() {
        System.out.print("\nAdd to cart? type y for yes or n for no: ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Enter SKU: ");
            String sku = scanner.nextLine();
            for (Product p : storeProducts) {
                if (p.getSku().equals(sku)) {
                    cart.addProductToCart(p);
                    return;
                }
            }
            System.out.println("Product not found!");
        }
    }

    //cartt screen
    private void displayCartScreen() {
        boolean goBack = false;
        while (!goBack) {

            List<Product> products = cart.getProducts();
            System.out.println("\n******************** YOUR CART ********************");
            if (products.isEmpty()) {
                System.out.println("Your cart is empty!");
            } else {
                for (Product p : products) {
                    System.out.println(p.getSku() + " - " + p.getProductName() + " - $" + p.getPrice());
                }
                System.out.println("Total: $" + cart.getCartTotal());
            }

            System.out.println("\n1. Checkout  2. Remove Item  3. Go Back");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (!products.isEmpty()) {
                        checkout();
                        goBack = true;
                    } else {
                        System.out.println("Cannot checkout with empty cart!");
                    }
                    break;
                case 2:
                    if (!products.isEmpty()) {
                        System.out.print("Enter SKU to remove: ");
                        cart.removeProductFromCart(scanner.nextLine());
                    } else {
                        System.out.println("Cart is already empty!");
                    }
                    break;
                case 3:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    //process checkout
    private void checkout() {
        System.out.println("\n******************** CHECKOUT ********************");
        double total = cart.getCartTotal();
        System.out.println("Total due: $" + total);

        System.out.print("Enter payment amount: $");
        double payment = scanner.nextDouble();
        scanner.nextLine();

        if (payment < total) {
            System.out.println("Insufficient payment!");
            return;
        }

        double change = payment - total;

        // print receipt
        System.out.println("\n******************** RECEIPT ********************");
        System.out.println("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        System.out.println("\nItems:");
        for (Product p : cart.getProducts()) {
            System.out.println(p.getSku() + " - " + p.getProductName() + " - $" + p.getPrice());
        }
        System.out.println("\nTotal: $" + total);
        System.out.println("Paid: $" + payment);
        System.out.println("Change: $" + change);

        cart.clearCart();
        System.out.println("\nThank you for your purchase!");
    }
}