package com.delicious.pos;

import com.delicious.pos.screens.ScreenNavigator;

public class DeliApp {
    private final ScreenNavigator navigator;
    private boolean isRunning;

    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";

    public DeliApp() {
        this.navigator = new ScreenNavigator();
        this.isRunning = true;
    }

    public void run() {
        showStartupScreen();

        while (isRunning) {
            navigator.displayCurrentScreen();
            if (navigator.isAtHomeScreen() && navigator.shouldExit()) {
                isRunning = false;
            }
        }

        showExitScreen(navigator.getCurrentCustomerName());
    }

    private void showStartupScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("\n\n");
        System.out.println(YELLOW + BOLD);
        System.out.println("    ╔════════════════════════════════════════════════════════════╗");
        System.out.println("    ║                                                            ║");
        System.out.println("    ║          YONINALDO'S DELICIOUS SANDWICHES                  ║");
        System.out.println("    ║           ═══════════════════════════════                  ║");
        System.out.println("    ║                                                            ║");
        System.out.println("    ║          Premium Sandwiches Made Fresh Daily               ║");
        System.out.println("    ║                                                            ║");
        System.out.println("    ╚════════════════════════════════════════════════════════════╝" + RESET);

        System.out.println("\n" + CYAN + "           Welcome to Yoninaldo's POS System" + RESET);
        System.out.println(GREEN + "                    Starting up..." + RESET);

        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.print(".");
            }
            Thread.sleep(500);
        } catch (InterruptedException e) {}

        System.out.println("\n" + GREEN + "                    ✓ Ready!" + RESET);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
    }

    private void showExitScreen(String customerName) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("\n\n");
        System.out.println(CYAN + "    ╔══════════════════════════════════════════════════════╗");
        System.out.println("    ║      Thank you for using Yoninaldo's POS!            ║");
        if (customerName != null && !customerName.isEmpty()) {
            String farewell = "Have a wonderful day, " + customerName + "!";
            int padding = (54 - farewell.length()) / 2;
            System.out.println("    ║" + " ".repeat(padding) + farewell + " ".repeat(54 - farewell.length() - padding) + "║");
        } else {
            System.out.println("    ║           Have a wonderful day!                      ║");
        }
        System.out.println("    ╚══════════════════════════════════════════════════════╝" + RESET);
        System.out.println("\n" + YELLOW + "                  Come back soon!" + RESET);
        System.out.println("\n");
    }

    public static void main(String[] args) {
        DeliApp app = new DeliApp();
        app.run();
    }
}