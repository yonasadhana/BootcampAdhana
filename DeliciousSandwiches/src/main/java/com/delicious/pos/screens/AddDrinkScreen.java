package com.delicious.pos.screens;

import com.delicious.pos.enums.DrinkFlavor;
import com.delicious.pos.enums.DrinkSize;
import com.delicious.pos.models.Drink;
import com.delicious.pos.models.Order;

public class AddDrinkScreen extends Screen {
    private final Order currentOrder;

    public AddDrinkScreen(ScreenNavigator navigator, Order order) {
        super(navigator);
        this.currentOrder = order;
    }

    @Override
    public void display() {
        clearScreen();
        printHeader("Add Drink");

        DrinkSize selectedSize = selectSize();
        if (selectedSize == null) {
            navigator.goBack();
            return;
        }

        DrinkFlavor selectedFlavor = selectFlavor();
        if (selectedFlavor == null) {
            navigator.goBack();
            return;
        }

        Drink drink = new Drink(selectedSize, selectedFlavor);
        currentOrder.addItem(drink);

        clearScreen();
        printHeader("Drink Added!");
        System.out.println("\nYour drink has been added to the order:");
        System.out.println(drink.getDescription());
        System.out.println("Price: $" + String.format("%.2f", drink.getPrice()));

        getStringInput("\nPress Enter to continue...");
        navigator.goBack();
    }

    private DrinkSize selectSize() {
        System.out.println("\nSelect drink size:");
        DrinkSize[] sizes = DrinkSize.values();
        for (int i = 0; i < sizes.length; i++) {
            System.out.println((i + 1) + ") " + sizes[i].getDisplayName() +
                    " - $" + String.format("%.2f", sizes[i].getPrice()));
        }
        System.out.println("0) Go back");

        int choice = getIntInput("\nSelect size: ");

        if (choice == 0) {
            return null;
        } else if (choice > 0 && choice <= sizes.length) {
            return sizes[choice - 1];
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectSize();
        }
    }

    private DrinkFlavor selectFlavor() {
        clearScreen();
        printHeader("Select Flavor");

        System.out.println("\nSelect drink flavor:");
        DrinkFlavor[] flavors = DrinkFlavor.values();
        for (int i = 0; i < flavors.length; i++) {
            System.out.println((i + 1) + ") " + flavors[i].getDisplayName());
        }
        System.out.println("0) Go back");

        int choice = getIntInput("\nSelect flavor: ");

        if (choice == 0) {
            return null;
        } else if (choice > 0 && choice <= flavors.length) {
            return flavors[choice - 1];
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectFlavor();
        }
    }
}