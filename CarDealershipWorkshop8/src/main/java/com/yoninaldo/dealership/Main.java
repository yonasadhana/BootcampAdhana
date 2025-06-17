package com.yoninaldo.dealership;

import com.yoninaldo.dealership.ui.UserInterface;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Car Dealership Application...");
        System.out.println("Connecting to database...");

        try {
            UserInterface ui = new UserInterface();
            ui.display();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            System.err.println("Make sure your MySQL database is running and the car_dealership database exists.");
            e.printStackTrace();
        }
    }
}