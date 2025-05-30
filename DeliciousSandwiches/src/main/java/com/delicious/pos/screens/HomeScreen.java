package com.delicious.pos.screens;

public class HomeScreen extends Screen {

    public HomeScreen(ScreenNavigator navigator) {
        super(navigator);
    }

    @Override
    public void display() {
        clearScreen();

        System.out.println("           " + CYAN + "Welcome to Yoninaldo's Delicious Sandwiches!" + RESET);
        System.out.println();

        String customerName = getStringInput("           May I have your name for the order? ");
        navigator.setCurrentCustomerName(customerName);

        System.out.println();
        System.out.println("           " + GREEN + "Thank you, " + customerName + "!" + RESET);

        System.out.println("\n    " + BOLD + "What would you like to do?" + RESET + "\n");

        printMenuOption("1", "New Order");
        printMenuOption("0", "Exit");

        System.out.println();
        printSeparator();

        int choice = getIntInput("\n➤ Please select an option: ");

        switch (choice) {
            case 1:
                printSuccess("Starting new order...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                navigator.navigateTo(new OrderScreen(navigator, customerName));
                break;
            case 0:
                System.out.println();
                printWarning("Are you sure you want to exit?");
                if (getYesNoInput("Exit application?")) {
                    System.out.println("\n" + CYAN + "    ╔══════════════════════════════════════════════════════╗");
                    System.out.println("    ║   Thank you for visiting Yoninaldo's Delicious       ║");
                    System.out.println("    ║                  Sandwiches!                         ║");
                    if (customerName != null && !customerName.isEmpty()) {
                        String farewell = "Come back soon, " + customerName + "!";
                        int padding = (54 - farewell.length()) / 2;
                        System.out.println("    ║" + " ".repeat(padding) + farewell + " ".repeat(54 - farewell.length() - padding) + "║");
                    } else {
                        System.out.println("    ║              Come back soon!                         ║");
                    }
                    System.out.println("    ╚══════════════════════════════════════════════════════╝" + RESET);
                    System.out.println();
                    navigator.setShouldExit(true);
                }
                break;
            default:
                printError("Invalid option. Please try again.");
                waitForEnter();
                display();
        }
    }
}