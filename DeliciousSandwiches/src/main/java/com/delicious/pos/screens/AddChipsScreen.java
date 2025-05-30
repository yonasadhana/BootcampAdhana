package com.delicious.pos.screens;

import com.delicious.pos.enums.ChipType;
import com.delicious.pos.models.Chips;
import com.delicious.pos.models.Order;

public class AddChipsScreen extends Screen {
    private final Order currentOrder;

    public AddChipsScreen(ScreenNavigator navigator, Order order) {
        super(navigator);
        this.currentOrder = order;
    }

    @Override
    public void display() {
        clearScreen();
        printHeader("Add Chips");

        System.out.println("\nSelect chip type:");
        ChipType[] chipTypes = ChipType.values();
        for (int i = 0; i < chipTypes.length; i++) {
            System.out.println((i + 1) + ") " + chipTypes[i].getDisplayName());
        }
        System.out.println("0) Go back");

        int choice = getIntInput("\nSelect chip type: ");

        if (choice == 0) {
            navigator.goBack();
        } else if (choice > 0 && choice <= chipTypes.length) {
            ChipType selectedType = chipTypes[choice - 1];
            Chips chips = new Chips(selectedType);
            currentOrder.addItem(chips);

            clearScreen();
            printHeader("Chips Added!");
            System.out.println("\nYour chips have been added to the order:");
            System.out.println(chips.getDescription());
            System.out.println("Price: $" + String.format("%.2f", chips.getPrice()));

            getStringInput("\nPress Enter to continue...");
            navigator.goBack();
        } else {
            System.out.println("Invalid selection. Please try again.");
            getStringInput("Press Enter to continue...");
            display();
        }
    }
}