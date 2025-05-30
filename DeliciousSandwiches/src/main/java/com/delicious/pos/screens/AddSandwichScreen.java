package com.delicious.pos.screens;

import com.delicious.pos.builders.SandwichBuilder;
import com.delicious.pos.models.*;
import com.delicious.pos.enums.*;

public class AddSandwichScreen extends Screen {
    private final Order currentOrder;
    private final SandwichBuilder sandwichBuilder;

    public AddSandwichScreen(ScreenNavigator navigator, Order order) {
        super(navigator);
        this.currentOrder = order;
        this.sandwichBuilder = new SandwichBuilder();
    }

    @Override
    public void display() {
        clearScreen();
        printHeader("Sandwich Builder");

        System.out.println(CYAN + "   Choose your sandwich adventure!" + RESET);
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n   " + BOLD + "How would you like to create your sandwich?" + RESET + "\n");

        printMenuOption("1", "Build Custom Sandwich");
        printMenuOption("2", "Order Signature Sandwich");
        System.out.println();
        printMenuOption("0", "Go back");

        System.out.println();
        printSeparator();

        int choice = getIntInput("\n➤ Please select an option: ");

        switch (choice) {
            case 1:
                printInfo("Let's build your perfect sandwich!");
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                createCustomSandwich();
                break;
            case 2:
                printInfo("Great choice! Check out our signatures...");
                try { Thread.sleep(500); } catch (InterruptedException e) {}
                selectSignatureSandwich();
                break;
            case 0:
                navigator.goBack();
                break;
            default:
                printError("Invalid option. Please try again.");
                waitForEnter();
                display();
        }
    }

    private void createCustomSandwich() {
        selectBread();
        selectSize();
        addMeats();
        addCheeses();
        addRegularToppings();
        addSauces();
        addSides();
        selectToasted();
        finalizeSandwich();
    }

    private void selectSignatureSandwich() {
        clearScreen();
        printHeader("Signature Sandwiches");

        System.out.println(CYAN + "   Our chef's special creations!" + RESET);
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n   " + BOLD + "Select a signature sandwich:" + RESET + "\n");

        System.out.println(GREEN + "  [1] " + YELLOW + BOLD + "BLT Classic" + RESET);
        System.out.println("      " + WHITE + "8\" white bread with crispy bacon, melted cheddar," + RESET);
        System.out.println("      " + WHITE + "fresh lettuce, ripe tomatoes, creamy ranch (toasted)" + RESET);

        System.out.println();

        System.out.println(GREEN + "  [2] " + YELLOW + BOLD + "Philly Cheese Steak" + RESET);
        System.out.println("      " + WHITE + "8\" white bread with tender steak, american cheese," + RESET);
        System.out.println("      " + WHITE + "sautéed peppers, savory mayo (toasted)" + RESET);

        System.out.println();
        printMenuOption("0", "Go back");

        System.out.println();
        printSeparator();

        int choice = getIntInput("\n➤ Please select an option: ");

        switch (choice) {
            case 1:
                sandwichBuilder.createBLT();
                printSuccess("BLT selected!");
                customizeSignatureSandwich("BLT");
                break;
            case 2:
                sandwichBuilder.createPhillyCheesesteak();
                printSuccess("Philly Cheese Steak selected!");
                customizeSignatureSandwich("Philly Cheese Steak");
                break;
            case 0:
                display();
                return;
            default:
                printError("Invalid option. Please try again.");
                waitForEnter();
                selectSignatureSandwich();
        }
    }

    private void customizeSignatureSandwich(String sandwichName) {
        System.out.println("\nYou selected: " + sandwichName);
        if (getYesNoInput("Would you like to customize this sandwich?")) {
            System.out.println("\nCustomization options:");
            System.out.println("1) Add extra toppings");
            System.out.println("2) Remove toppings");
            System.out.println("3) Keep as is");

            int choice = getIntInput("\nPlease select an option: ");

            switch (choice) {
                case 1:
                    addExtraToppings();
                    break;
                case 2:
                    removeToppings();
                    break;
            }
        }
        finalizeSandwich();
    }

    private void selectBread() {
        clearScreen();
        printHeader("Select Your Bread");

        System.out.println(CYAN + "   The foundation of every great sandwich!" + RESET);
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n   " + YELLOW + BOLD + "AVAILABLE BREADS" + RESET + "\n");

        BreadType[] breads = BreadType.values();
        for (int i = 0; i < breads.length; i++) {
            System.out.println("  " + GREEN + "[" + (i + 1) + "]" + RESET + " " + breads[i].getDisplayName());
        }

        int choice = getIntInput("\n➤ Select bread type: ");
        if (choice > 0 && choice <= breads.length) {
            sandwichBuilder.setBread(breads[choice - 1]);
            printSuccess("Selected: " + breads[choice - 1].getDisplayName());
            System.out.println(CYAN + "\nPress Enter to continue..." + RESET);
            scanner.nextLine();
        } else {
            printError("Invalid selection. Please try again.");
            waitForEnter();
            selectBread();
        }
    }

    private void selectSize() {
        clearScreen();
        printHeader("Select Size");

        System.out.println(CYAN + "   Choose your hunger level!" + RESET);
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n   " + CYAN + BOLD + "SANDWICH SIZES" + RESET + "\n");

        SandwichSize[] sizes = SandwichSize.values();
        String[] sizeDescriptions = {"Perfect for a light meal", "Our most popular size", "For the serious appetite"};

        for (int i = 0; i < sizes.length; i++) {
            System.out.println("  " + GREEN + "[" + (i + 1) + "]" + RESET + " " + YELLOW + sizes[i].getDisplayName() +
                    GREEN + " $" + String.format("%.2f", sizes[i].getBasePrice()) + RESET +
                    " - " + WHITE + sizeDescriptions[i] + RESET);
        }

        int choice = getIntInput("\n➤ Select size: ");
        if (choice > 0 && choice <= sizes.length) {
            sandwichBuilder.setSize(sizes[choice - 1]);
            printSuccess("Selected: " + sizes[choice - 1].getDisplayName());
            System.out.println(CYAN + "\nPress Enter to continue..." + RESET);
            scanner.nextLine();
        } else {
            printError("Invalid selection. Please try again.");
            waitForEnter();
            selectSize();
        }
    }

    private void addMeats() {
        clearScreen();
        printHeader("Add Meats");

        System.out.println("\nWould you like to add meat to your sandwich?");
        if (getYesNoInput("Add meat?")) {
            boolean addingMeats = true;
            while (addingMeats) {
                System.out.println("\n   " + RED + BOLD + "PREMIUM MEATS" + RESET + "\n");

                MeatType[] meats = MeatType.values();
                for (int i = 0; i < meats.length; i++) {
                    System.out.println("  " + GREEN + "[" + (i + 1) + "]" + RESET + " " + meats[i].getDisplayName());
                }
                System.out.println("  " + YELLOW + "[0]" + RESET + " Done adding meats");

                int choice = getIntInput("\n➤ Select meat: ");
                if (choice == 0) {
                    addingMeats = false;
                } else if (choice > 0 && choice <= meats.length) {
                    MeatType selectedMeat = meats[choice - 1];
                    boolean extra = getYesNoInput("Would you like extra " + selectedMeat.getDisplayName() + "?");
                    sandwichBuilder.addMeat(new Meat(selectedMeat, extra));
                    printSuccess(selectedMeat.getDisplayName() + " added" + (extra ? " (extra)" : ""));
                }
            }
        }
    }

    private void addCheeses() {
        clearScreen();
        printHeader("Add Cheese");

        System.out.println("\nWould you like to add cheese to your sandwich?");
        if (getYesNoInput("Add cheese?")) {
            boolean addingCheese = true;
            while (addingCheese) {
                System.out.println("\n   " + YELLOW + BOLD + "PREMIUM CHEESES" + RESET + "\n");

                CheeseType[] cheeses = CheeseType.values();
                for (int i = 0; i < cheeses.length; i++) {
                    System.out.println("  " + GREEN + "[" + (i + 1) + "]" + RESET + " " + cheeses[i].getDisplayName());
                }
                System.out.println("  " + CYAN + "[0]" + RESET + " Done adding cheese");

                int choice = getIntInput("\n➤ Select cheese: ");
                if (choice == 0) {
                    addingCheese = false;
                } else if (choice > 0 && choice <= cheeses.length) {
                    CheeseType selectedCheese = cheeses[choice - 1];
                    boolean extra = getYesNoInput("Would you like extra " + selectedCheese.getDisplayName() + "?");
                    sandwichBuilder.addCheese(new Cheese(selectedCheese, extra));
                    printSuccess(selectedCheese.getDisplayName() + " added" + (extra ? " (extra)" : ""));
                }
            }
        }
    }

    private void addRegularToppings() {
        clearScreen();
        printHeader("Fresh Vegetables & Toppings");

        printInfo("All regular toppings are FREE! Load up your sandwich!");
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n   " + GREEN + BOLD + "AVAILABLE VEGETABLES" + RESET + "\n");

        VegetableType[] vegetables = VegetableType.values();
        for (int i = 0; i < vegetables.length; i++) {
            System.out.println("  " + CYAN + "[" + (i + 1) + "]" + RESET + " " + vegetables[i].getDisplayName());
        }

        System.out.println("\n" + CYAN + "Enter the numbers of toppings you want (separated by commas)" + RESET);
        System.out.println(CYAN + "Example: 1, 3, 5" + RESET);
        System.out.println(CYAN + "Type '0' or press Enter for no toppings" + RESET);

        String input = getStringInput("\n➤ Your selection: ").trim();

        if (!input.equals("0") && !input.isEmpty()) {
            String[] selectedNumbers = input.split(",");
            System.out.println();

            for (String number : selectedNumbers) {
                try {
                    int choice = Integer.parseInt(number.trim());
                    if (choice > 0 && choice <= vegetables.length) {
                        VegetableType selectedVeg = vegetables[choice - 1];
                        sandwichBuilder.addVegetable(new Vegetable(selectedVeg));
                        printSuccess("Added: " + selectedVeg.getDisplayName());
                    } else if (choice != 0) {
                        printError("Invalid number: " + choice + " (skipped)");
                    }
                } catch (NumberFormatException e) {
                    printError("Invalid input: " + number.trim() + " (skipped)");
                }
            }
        }
        System.out.println(CYAN + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    private void addSauces() {
        clearScreen();
        printHeader("Add Sauces");

        printInfo("All sauces are FREE! Choose your favorites!");
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n   " + PURPLE + BOLD + "AVAILABLE SAUCES" + RESET + "\n");

        SauceType[] sauces = SauceType.values();
        for (int i = 0; i < sauces.length; i++) {
            System.out.println("  " + GREEN + "[" + (i + 1) + "]" + RESET + " " + sauces[i].getDisplayName());
        }

        System.out.println("\n" + CYAN + "Enter the numbers of sauces you want (separated by commas)" + RESET);
        System.out.println(CYAN + "Example: 1, 2, 4" + RESET);
        System.out.println(CYAN + "Type '0' or press Enter for no sauces" + RESET);

        String input = getStringInput("\n➤ Your selection: ").trim();

        if (!input.equals("0") && !input.isEmpty()) {
            String[] selectedNumbers = input.split(",");
            System.out.println();

            for (String number : selectedNumbers) {
                try {
                    int choice = Integer.parseInt(number.trim());
                    if (choice > 0 && choice <= sauces.length) {
                        SauceType selectedSauce = sauces[choice - 1];
                        sandwichBuilder.addSauce(new Sauce(selectedSauce));
                        printSuccess("Added: " + selectedSauce.getDisplayName());
                    } else if (choice != 0) {
                        printError("Invalid number: " + choice + " (skipped)");
                    }
                } catch (NumberFormatException e) {
                    printError("Invalid input: " + number.trim() + " (skipped)");
                }
            }
        }
        System.out.println(CYAN + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    private void addSides() {
        clearScreen();
        printHeader("Add Sides");

        printInfo("All sides are FREE! Perfect for dipping!");
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n   " + BLUE + BOLD + "AVAILABLE SIDES" + RESET + "\n");

        SideType[] sides = SideType.values();
        for (int i = 0; i < sides.length; i++) {
            System.out.println("  " + GREEN + "[" + (i + 1) + "]" + RESET + " " + sides[i].getDisplayName());
        }

        System.out.println("\n" + CYAN + "Enter the numbers of sides you want (separated by commas)" + RESET);
        System.out.println(CYAN + "Example: 1, 2" + RESET);
        System.out.println(CYAN + "Type '0' or press Enter for no sides" + RESET);

        String input = getStringInput("\n➤ Your selection: ").trim();

        if (!input.equals("0") && !input.isEmpty()) {
            String[] selectedNumbers = input.split(",");
            System.out.println();

            for (String number : selectedNumbers) {
                try {
                    int choice = Integer.parseInt(number.trim());
                    if (choice > 0 && choice <= sides.length) {
                        SideType selectedSide = sides[choice - 1];
                        sandwichBuilder.addSide(new Side(selectedSide));
                        printSuccess("Added: " + selectedSide.getDisplayName());
                    } else if (choice != 0) {
                        printError("Invalid number: " + choice + " (skipped)");
                    }
                } catch (NumberFormatException e) {
                    printError("Invalid input: " + number.trim() + " (skipped)");
                }
            }
        }
        System.out.println(CYAN + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    private void selectToasted() {
        clearScreen();
        printHeader("Toast Option");

        if (getYesNoInput("\nWould you like your sandwich toasted?")) {
            sandwichBuilder.setToasted(true);
        }
    }

    private void addExtraToppings() {
        System.out.println("\nWhat would you like to add?");
        System.out.println("1) Extra meat");
        System.out.println("2) Extra cheese");
        System.out.println("3) Vegetables");
        System.out.println("4) Sauces");
        System.out.println("5) Sides");

        int choice = getIntInput("\nSelect option: ");

        switch (choice) {
            case 1:
                addMeats();
                break;
            case 2:
                addCheeses();
                break;
            case 3:
                addRegularToppings();
                break;
            case 4:
                addSauces();
                break;
            case 5:
                addSides();
                break;
        }
    }

    private void removeToppings() {
        System.out.println("\nTopping removal feature coming soon!");
        waitForEnter();
    }

    private void finalizeSandwich() {
        Sandwich sandwich = sandwichBuilder.build();
        currentOrder.addItem(sandwich);

        clearScreen();
        printHeader("Sandwich Complete!");

        System.out.println(GREEN + BOLD + "   Your masterpiece is ready!" + RESET);
        System.out.println();
        printDoubleSeparator();

        System.out.println("\n" + BOLD + "Your sandwich:" + RESET);
        System.out.println(CYAN + "   " + sandwich.getDescription() + RESET);

        System.out.println("\n" + BOLD + "Details:" + RESET);
        String details = sandwich.getDetailedDescription();
        String[] lines = details.split("\n");
        for (String line : lines) {
            if (line.contains(":")) {
                System.out.println(YELLOW + "   " + line + RESET);
            } else {
                System.out.println(WHITE + "   " + line + RESET);
            }
        }

        System.out.println();
        printSeparator();
        System.out.println(BOLD + GREEN + "   Price: $" + String.format("%.2f", sandwich.getPrice()) + RESET);
        printSeparator();

        waitForEnter();
        navigator.goBack();
    }
}