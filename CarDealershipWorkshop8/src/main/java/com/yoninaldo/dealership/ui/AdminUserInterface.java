package com.yoninaldo.dealership.ui;

import com.yoninaldo.dealership.dao.ContractDao;
import com.yoninaldo.dealership.model.*;

import java.util.List;
import java.util.Scanner;

public class AdminUserInterface {
    private final Scanner scanner;
    private final ContractDao contractDao;
    private static final String ADMIN_PASSWORD = "yonina7do";

    public AdminUserInterface(Scanner scanner, ContractDao contractDao) {
        this.scanner = scanner;
        this.contractDao = contractDao;
    }

    public boolean authenticate() {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        return password.equals(ADMIN_PASSWORD);
    }

    public void display() {
        boolean exit = false;

        while (!exit) {
            displayMenu();

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listAllContracts();
                    break;
                case 2:
                    listLastTenContracts();
                    break;
                case 99:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
                System.out.println("-------------------------------");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1 - List All Contracts");
        System.out.println("2 - List Last 10 Contracts");
        System.out.println("99 - Return to Main Menu");
    }

    private void listAllContracts() {
        List<Contract> contracts = contractDao.getAllContracts();

        if (contracts.isEmpty()) {
            System.out.println("No contracts found.");
            return;
        }

        System.out.println("\nAll Contracts:");
        System.out.println("-------------------------------");

        displayContracts(contracts);
    }

    private void listLastTenContracts() {
        List<Contract> allContracts = contractDao.getAllContracts();

        if (allContracts.isEmpty()) {
            System.out.println("No contracts found.");
            return;
        }

        System.out.println("\nLast 10 Contracts:");
        System.out.println("-------------------------------");

        int endIndex = Math.min(10, allContracts.size());
        List<Contract> lastTenContracts = allContracts.subList(0, endIndex);

        displayContracts(lastTenContracts);
    }

    private void displayContracts(List<Contract> contracts) {
        for (int i = 0; i < contracts.size(); i++) {
            Contract contract = contracts.get(i);

            System.out.println("Contract #" + (i + 1));
            System.out.println("Type: " + (contract instanceof SalesContract ? "Sales" : "Lease"));
            System.out.println("Date: " + contract.getDate());
            System.out.println("Customer: " + contract.getCustomerName());
            System.out.println("Email: " + contract.getCustomerEmail());

            Vehicle vehicle = contract.getVehicle();
            System.out.println("Vehicle: " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
            System.out.println("VIN: " + vehicle.getVin());
            System.out.println("Price: $" + String.format("%.2f", vehicle.getPrice()));

            if (contract instanceof SalesContract) {
                SalesContract salesContract = (SalesContract) contract;
                System.out.println("Financed: " + (salesContract.isFinance() ? "Yes" : "No"));

                List<AddOn> addOns = salesContract.getAddOns();
                if (!addOns.isEmpty()) {
                    System.out.println("Add-ons:");
                    for (AddOn addOn : addOns) {
                        System.out.println("  - " + addOn.getName() + ": $" +
                                String.format("%.2f", addOn.getPrice()));
                    }
                }
            } else if (contract instanceof LeaseContract) {
                LeaseContract leaseContract = (LeaseContract) contract;
                System.out.println("Expected Ending Value: $" +
                        String.format("%.2f", leaseContract.getExpectedEndingValue()));
                System.out.println("Lease Fee: $" + String.format("%.2f", leaseContract.getLeaseFee()));
            }

            System.out.println("Total Price: $" + String.format("%.2f", contract.getTotalPrice()));
            System.out.println("Monthly Payment: $" + String.format("%.2f", contract.getMonthlyPayment()));
            System.out.println("-------------------------------");
        }
    }
}