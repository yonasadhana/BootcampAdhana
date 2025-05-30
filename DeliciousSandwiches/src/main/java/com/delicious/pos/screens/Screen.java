package com.delicious.pos.screens;

import java.util.Scanner;

public abstract class Screen {
    protected final ScreenNavigator navigator;
    protected final Scanner scanner;

    protected static final String RESET = "\u001B[0m";
    protected static final String RED = "\u001B[31m";
    protected static final String GREEN = "\u001B[32m";
    protected static final String YELLOW = "\u001B[33m";
    protected static final String BLUE = "\u001B[34m";
    protected static final String PURPLE = "\u001B[35m";
    protected static final String CYAN = "\u001B[36m";
    protected static final String WHITE = "\u001B[37m";
    protected static final String BOLD = "\u001B[1m";
    protected static final String BG_BLACK = "\u001B[40m";
    protected static final String BG_RED = "\u001B[41m";
    protected static final String BG_GREEN = "\u001B[42m";
    protected static final String BG_YELLOW = "\u001B[43m";
    protected static final String BG_BLUE = "\u001B[44m";

    public Screen(ScreenNavigator navigator) {
        this.navigator = navigator;
        this.scanner = new Scanner(System.in);
    }

    public abstract void display();

    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n");
    }

    protected void printHeader(String title) {
        String border = "═".repeat(60);
        System.out.println("    " + CYAN + BOLD + "╔" + border + "╗" + RESET);
        System.out.println("    " + CYAN + BOLD + "║" + RESET + centerText(title, 60) + CYAN + BOLD + "║" + RESET);
        System.out.println("    " + CYAN + BOLD + "╚" + border + "╝" + RESET);
        System.out.println();
    }

    protected void printSeparator() {
        System.out.println("    " + BLUE + "─".repeat(62) + RESET);
    }

    protected void printDoubleSeparator() {
        System.out.println("    " + YELLOW + "═".repeat(62) + RESET);
    }

    protected String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        String paddedText = " ".repeat(padding) + YELLOW + BOLD + text + RESET + " ".repeat(padding);
        if (text.length() % 2 != width % 2) {
            paddedText += " ";
        }
        return paddedText;
    }

    protected void printMenuOption(String number, String text) {
        System.out.println("      " + GREEN + BOLD + "[" + number + "]" + RESET + " " + text);
    }

    protected void printMenuOption(String number, String text, String price) {
        System.out.println("      " + GREEN + BOLD + "[" + number + "]" + RESET + " " + text +
                " " + YELLOW + "($" + price + ")" + RESET);
    }

    protected void printError(String message) {
        System.out.println("    " + RED + BOLD + message + RESET);
    }

    protected void printSuccess(String message) {
        System.out.println("    " + GREEN + BOLD + message + RESET);
    }

    protected void printInfo(String message) {
        System.out.println("    " + CYAN + message + RESET);
    }

    protected void printWarning(String message) {
        System.out.println("    " + YELLOW + message + RESET);
    }

    protected int getIntInput(String prompt) {
        while (true) {
            System.out.print(PURPLE + prompt + RESET);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                printError("Invalid input. Please enter a number.");
            }
        }
    }

    protected String getStringInput(String prompt) {
        System.out.print(PURPLE + prompt + RESET);
        return scanner.nextLine().trim();
    }

    protected boolean getYesNoInput(String prompt) {
        while (true) {
            System.out.print(PURPLE + prompt + " (Y/N): " + RESET);
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            } else if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            printError("Please enter Y for Yes or N for No.");
        }
    }

    protected void waitForEnter() {
        System.out.print(CYAN + "\n    Press Enter to continue..." + RESET);
        scanner.nextLine();
    }

    protected void printLogo() {
        System.out.println(YELLOW + BOLD);
        System.out.println("    ╔════════════════════════════════════════════════════════════╗");
        System.out.println("    ║                                                            ║");
        System.out.println("    ║         YONINALDO'S DELICIOUS SANDWICHES                   ║");
        System.out.println("    ║         ═══════════════════════════════                    ║");
        System.out.println("    ║         Premium Sandwiches Made Fresh Daily                ║");
        System.out.println("    ║                                                            ║");
        System.out.println("    ╚════════════════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }
}