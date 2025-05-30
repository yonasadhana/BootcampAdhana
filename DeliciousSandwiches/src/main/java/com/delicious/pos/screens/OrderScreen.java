package com.delicious.pos.screens;

import com.delicious.pos.models.Order;
import com.delicious.pos.models.OrderItem;

public class OrderScreen extends Screen {
    private final Order currentOrder;
    private final String customerName;

    public OrderScreen(ScreenNavigator navigator, String customerName) {
        super(navigator);
        this.customerName = customerName;
        this.currentOrder = new Order(customerName);
    }

    @Override
    public void display() {
        clearScreen();
        printHeader("Order Menu");

        System.out.println("    " + CYAN + "Customer: " + WHITE + customerName + RESET);

        if (!currentOrder.isEmpty()) {
            System.out.println("\n    " + BOLD + CYAN + "Current Order:" + RESET);
            printSeparator();
            displayOrderWithNumbers();
            printSeparator();
            System.out.println("    " + BOLD + GREEN + "TOTAL: $" + String.format("%.2f", currentOrder.getTotal()) + RESET);
            printDoubleSeparator();
        } else {
            System.out.println();
            printInfo("Your order is empty. Add some delicious items!");
            printSeparator();
        }

        System.out.println("\n    " + BOLD + "What would you like to add?" + RESET + "\n");

        printMenuOption("1", "Add Sandwich");
        printMenuOption("2", "Add Drink");
        printMenuOption("3", "Add Chips");

        if (!currentOrder.isEmpty()) {
            System.out.println();
            printMenuOption("4", "Remove Item");
            printMenuOption("5", "Checkout");
        } else {
            printMenuOption("4", "Checkout");
        }

        System.out.println();
        printMenuOption("0", "Cancel Order");

        System.out.println();
        printSeparator();

        int choice = getIntInput("\n➤ Please select an option: ");

        if (currentOrder.isEmpty() && choice == 5) {
            choice = 4;
        }

        switch (choice) {
            case 1:
                printInfo("Opening sandwich menu...");
                navigator.navigateTo(new AddSandwichScreen(navigator, currentOrder));
                break;
            case 2:
                printInfo("Opening drinks menu...");
                navigator.navigateTo(new AddDrinkScreen(navigator, currentOrder));
                break;
            case 3:
                printInfo("Opening chips menu...");
                navigator.navigateTo(new AddChipsScreen(navigator, currentOrder));
                break;
            case 4:
                if (!currentOrder.isEmpty()) {
                    removeItem();
                } else {
                    printWarning("Your order is empty. Please add items first.");
                    waitForEnter();
                    display();
                }
                break;
            case 5:
                if (!currentOrder.isEmpty()) {
                    printInfo("Proceeding to checkout...");
                    navigator.navigateTo(new CheckoutScreen(navigator, currentOrder));
                } else {
                    printError("Invalid option. Please try again.");
                    waitForEnter();
                    display();
                }
                break;
            case 0:
                System.out.println();
                printWarning("Are you sure you want to cancel this order?");
                if (getYesNoInput("Cancel order?")) {
                    printInfo("Order cancelled. Returning to home...");
                    navigator.goHome();
                } else {
                    display();
                }
                break;
            default:
                printError("Invalid option. Please try again.");
                waitForEnter();
                display();
        }
    }

    private void displayOrderWithNumbers() {
        int itemNumber = 1;
        for (OrderItem item : currentOrder.getItems()) {
            System.out.println("       " + YELLOW + itemNumber + "." + RESET + " " +
                    WHITE + item.getDescription() + RESET +
                    " - " + GREEN + "$" + String.format("%.2f", item.getPrice()) + RESET);
            itemNumber++;
        }
    }

    private void removeItem() {
        clearScreen();
        printHeader("Remove Item");

        System.out.println("    " + BOLD + CYAN + "Current Order:" + RESET);
        printSeparator();
        displayOrderWithNumbers();
        printSeparator();

        System.out.println();
        printMenuOption("0", "Go back");

        int choice = getIntInput("\n➤ Enter item number to remove: ");

        if (choice == 0) {
            display();
        } else if (choice > 0 && choice <= currentOrder.getItems().size()) {
            OrderItem removedItem = currentOrder.removeItem(choice - 1);
            printSuccess("Removed: " + removedItem.getDescription());
            waitForEnter();
            display();
        } else {
            printError("Invalid item number.");
            waitForEnter();
            removeItem();
        }
    }
}