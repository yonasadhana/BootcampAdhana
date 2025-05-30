package com.delicious.pos.screens;

import java.util.Stack;

public class ScreenNavigator {
    private final Stack<Screen> screenStack;
    private Screen currentScreen;
    private boolean shouldExit;
    private String currentCustomerName;

    public ScreenNavigator() {
        this.screenStack = new Stack<>();
        this.shouldExit = false;
        this.currentScreen = new HomeScreen(this);
    }

    public void displayCurrentScreen() {
        if (currentScreen != null) {
            currentScreen.display();
        }
    }

    public void navigateTo(Screen newScreen) {
        if (currentScreen != null) {
            screenStack.push(currentScreen);
        }
        currentScreen = newScreen;
    }

    public void goBack() {
        if (!screenStack.isEmpty()) {
            currentScreen = screenStack.pop();
        }
    }

    public void goHome() {
        screenStack.clear();
        currentScreen = new HomeScreen(this);
    }

    public boolean isAtHomeScreen() {
        return currentScreen instanceof HomeScreen;
    }

    public void setShouldExit(boolean shouldExit) {
        this.shouldExit = shouldExit;
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    public void setCurrentCustomerName(String customerName) {
        this.currentCustomerName = customerName;
    }

    public String getCurrentCustomerName() {
        return currentCustomerName;
    }
}