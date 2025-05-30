package com.delicious.pos.screens;

import com.delicious.pos.models.Order;
import com.delicious.pos.models.Receipt;
import com.delicious.pos.utils.ReceiptWriter;

public class CheckoutScreen extends Screen {
    private final Order currentOrder;

    public CheckoutScreen(ScreenNavigator navigator, Order order) {
        super(navigator);
        this.currentOrder = order;
    }

    @Override
    public void display() {
        clearScreen();
        printHeader("Checkout");

        System.out.println("    " + CYAN + "Customer: " + WHITE + currentOrder.getCustomerName() + RESET);
        System.out.println();

        System.out.println("    " + CYAN + BOLD + "Ready to complete your order?" + RESET);
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n    " + BOLD + "Order Summary:" + RESET);
        printSeparator();

        String[] orderLines = currentOrder.getOrderSummary().split("\n");
        for (String line : orderLines) {
            if (line.contains("$")) {
                String[] parts = line.split(" - \\$");
                System.out.println("       " + WHITE + parts[0] + RESET + " - " + GREEN + "$" + parts[1] + RESET);
            } else {
                System.out.println("       " + line);
            }
        }

        printSeparator();
        System.out.println("    " + BOLD + GREEN + "TOTAL: $" + String.format("%.2f", currentOrder.getTotal()) + RESET);
        printDoubleSeparator();

        System.out.println("\n    " + BOLD + "Complete your order?" + RESET + "\n");

        printMenuOption("1", "Confirm order");
        printMenuOption("2", "Cancel order");

        System.out.println();
        printSeparator();

        int choice = getIntInput("\n➤ Please select an option: ");

        switch (choice) {
            case 1:
                confirmOrder();
                break;
            case 2:
                cancelOrder();
                break;
            default:
                printError("Invalid option. Please try again.");
                waitForEnter();
                display();
        }
    }

    private void confirmOrder() {
        try {
            System.out.println();
            printInfo("Processing your order...");

            Receipt receipt = new Receipt(currentOrder);
            ReceiptWriter writer = new ReceiptWriter();
            writer.writeReceipt(receipt);

            try { Thread.sleep(1000); } catch (InterruptedException e) {}

            clearScreen();
            printHeader("Order Complete!");

            System.out.println("    " + GREEN + BOLD + "Thank you for your order!" + RESET);
            System.out.println();
            printDoubleSeparator();

            System.out.println("\n    " + CYAN + "Receipt saved as: " + YELLOW + receipt.getFileName() + RESET);
            System.out.println("\n    " + GREEN + "Total charged: $" + String.format("%.2f", currentOrder.getTotal()) + RESET);

            System.out.println();
            printSeparator();
            System.out.println("\n    " + YELLOW + "Thank you for choosing Yoninaldo's, " + currentOrder.getCustomerName() + "!" + RESET);
            System.out.println("    " + CYAN + "Enjoy your meal!" + RESET);

            waitForEnter();
            navigator.goHome();
        } catch (Exception e) {
            printError("Error saving receipt: " + e.getMessage());
            printWarning("Order completed but receipt could not be saved.");
            waitForEnter();
            navigator.goHome();
        }
    }

    private void cancelOrder() {
        System.out.println();
        printWarning("Are you sure you want to cancel this order?");
        if (getYesNoInput("Cancel order?")) {
            printInfo("Order cancelled.");
            waitForEnter();
            navigator.goHome();
        } else {
            display();
        }
    }
}