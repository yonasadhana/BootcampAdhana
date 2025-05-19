package com.yoninaldo.dealership;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class UserInterface {
    private Dealership dealership;
    private final Scanner scanner = new Scanner(System.in);
    private final DealershipFileManager fileManager;
    private final ContractFileManager contractManager;
    private final AddOnManager addOnManager;

    public UserInterface() {
        fileManager = new DealershipFileManager();
        contractManager = new ContractFileManager();
        addOnManager = new AddOnManager();
    }

    public UserInterface(String dealershipFilePath, String contractFilePath) {
        fileManager = new DealershipFileManager(dealershipFilePath);
        contractManager = new ContractFileManager(contractFilePath);
        addOnManager = new AddOnManager();
    }

    private void init() {
        this.dealership = fileManager.getDealership();

        System.out.println("---------------------------------------------");
        System.out.println(" Welcome to " + dealership.getName() + "! ");
        System.out.println(" Located at: " + dealership.getAddress() + " ");
        System.out.println(" Phone: " + dealership.getPhone() + " ");
        System.out.println("---------------------------------------------");
    }

    public void display() {
        init();

        boolean exit = false;

        while (!exit) {
            displayMenu();

            System.out.print("Enter the number of your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10:
                    processSellLeaseVehicleRequest();
                    break;
                case 11:
                    processAdminRequest();
                    break;
                case 99:
                    exit = true;
                    System.out.println("Thank you for using the Dealership App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
                System.out.println("-------------------------------");
            }
        }

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1 - Find vehicles within a price range");
        System.out.println("2 - Find vehicles by make/model");
        System.out.println("3 - Find vehicles by year range");
        System.out.println("4 - Find vehicles by color");
        System.out.println("5 - Find vehicles by mileage range");
        System.out.println("6 - Find vehicles by type (car, truck, SUV, van)");
        System.out.println("7 - List ALL vehicles");
        System.out.println("8 - Add a vehicle");
        System.out.println("9 - Remove a vehicle");
        System.out.println("10 - Sell/Lease a vehicle");
        System.out.println("11 - Admin");
        System.out.println("99 - Quit");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        System.out.println("\nVehicles Found: " + vehicles.size());
        System.out.println("-------------------------------");

        for (Vehicle vehicle : vehicles) {
            System.out.println("VIN: " + vehicle.getVin());
            System.out.println("Year: " + vehicle.getYear());
            System.out.println("Make: " + vehicle.getMake());
            System.out.println("Model: " + vehicle.getModel());
            System.out.println("Type: " + vehicle.getVehicleType());
            System.out.println("Color: " + vehicle.getColor());
            System.out.println("Mileage: " + vehicle.getOdometer());
            System.out.println("Price: $" + vehicle.getPrice());
            System.out.println("-------------------------------");
        }
    }

    public void processGetAllVehiclesRequest() {
        System.out.println("\nAll vehicles:");
        List<Vehicle> allVehicles = dealership.getAllVehicles();
        displayVehicles(allVehicles);
    }

    public void processGetByPriceRequest() {
        System.out.println("\nFind by price range:");

        System.out.print("Minimum price: $");
        double minPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Maximum price: $");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByPrice(minPrice, maxPrice);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        System.out.println("\nFind by make and model:");

        System.out.print("Make: ");
        String make = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        System.out.println("\nFind by year range:");

        System.out.print("Minimum year: ");
        int minYear = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Maximum year: ");
        int maxYear = scanner.nextInt();
        scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByYear(minYear, maxYear);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        System.out.println("\nFind by color:");

        System.out.print("Color: ");
        String color = scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        System.out.println("\nFind by mileage range:");

        System.out.print("Minimum mileage: ");
        int minMileage = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Maximum mileage: ");
        int maxMileage = scanner.nextInt();
        scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByMileage(minMileage, maxMileage);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.println("\nFind by type:");
        System.out.println("Available types: CAR, TRUCK, SUV, VAN");

        System.out.print("Type: ");
        String typeInput = scanner.nextLine().toUpperCase();

        try {
            VehicleType selectedType = VehicleType.valueOf(typeInput);
            List<Vehicle> vehicles = dealership.getVehiclesByType(selectedType);
            displayVehicles(vehicles);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle type.");
        }
    }

    public void processAddVehicleRequest() {
        System.out.println("\nAdd new vehicle:");

        try {
            System.out.print("VIN: ");
            int vin = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Year: ");
            int year = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Make: ");
            String make = scanner.nextLine();

            System.out.print("Model: ");
            String model = scanner.nextLine();

            System.out.println("Available types: CAR, TRUCK, SUV, VAN");
            System.out.print("Type: ");
            String typeString = scanner.nextLine().toUpperCase();
            VehicleType vehicleType = VehicleType.valueOf(typeString);

            System.out.print("Color: ");
            String color = scanner.nextLine();

            System.out.print("Odometer: ");
            int odometer = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Price: $");
            double price = scanner.nextDouble();
            scanner.nextLine();

            Vehicle newVehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
            dealership.addVehicle(newVehicle);

            fileManager.saveDealership(dealership);

            System.out.println("\nVehicle added!");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void processRemoveVehicleRequest() {
        System.out.println("\nRemove vehicle:");

        try {
            processGetAllVehiclesRequest();

            System.out.print("\nVIN to remove: ");
            int vinToRemove = scanner.nextInt();
            scanner.nextLine();

            Vehicle vehicleToRemove = null;
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                if (vehicle.getVin() == vinToRemove) {
                    vehicleToRemove = vehicle;
                    break;
                }
            }

            if (vehicleToRemove != null) {
                System.out.println("\nFound vehicle:");
                System.out.println("VIN: " + vehicleToRemove.getVin());
                System.out.println("Year: " + vehicleToRemove.getYear());
                System.out.println("Make: " + vehicleToRemove.getMake());
                System.out.println("Model: " + vehicleToRemove.getModel());

                System.out.print("\nConfirm removal (y/n): ");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("y")) {
                    dealership.removeVehicle(vehicleToRemove);
                    fileManager.saveDealership(dealership);
                    System.out.println("Vehicle removed!");
                } else {
                    System.out.println("Removal cancelled.");
                }
            } else {
                System.out.println("No vehicle found with VIN: " + vinToRemove);
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void processSellLeaseVehicleRequest() {
        System.out.println("\nSell/Lease a Vehicle:");

        try {
            processGetAllVehiclesRequest();

            System.out.print("\nEnter the VIN of the vehicle: ");
            int vin = scanner.nextInt();
            scanner.nextLine();

            Vehicle vehicle = null;
            for (Vehicle v : dealership.getAllVehicles()) {
                if (v.getVin() == vin) {
                    vehicle = v;
                    break;
                }
            }

            if (vehicle == null) {
                System.out.println("No vehicle found with that VIN.");
                return;
            }

            System.out.println("\nFound vehicle:");
            System.out.println("VIN: " + vehicle.getVin());
            System.out.println("Year: " + vehicle.getYear());
            System.out.println("Make: " + vehicle.getMake());
            System.out.println("Model: " + vehicle.getModel());
            System.out.println("Price: $" + vehicle.getPrice());

            System.out.print("\nEnter customer name: ");
            String customerName = scanner.nextLine();

            System.out.print("Enter customer email: ");
            String customerEmail = scanner.nextLine();

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String date = today.format(formatter);

            System.out.print("Is this a lease or sale? (L/S): ");
            String contractType = scanner.nextLine().trim().toUpperCase();

            Contract contract = null;

            if (contractType.equals("L")) {
                int currentYear = LocalDate.now().getYear();
                if (currentYear - vehicle.getYear() > 3) {
                    System.out.println("Cannot lease a vehicle over 3 years old.");
                    return;
                }

                contract = new LeaseContract(date, customerName, customerEmail, vehicle);

            } else if (contractType.equals("S")) {
                System.out.print("Will the vehicle be financed? (Y/N): ");
                String financeOption = scanner.nextLine().trim().toUpperCase();
                boolean finance = financeOption.equals("Y");

                SalesContract salesContract = new SalesContract(date, customerName, customerEmail, vehicle, finance);

                offerAddOns(salesContract);

                contract = salesContract;

            } else {
                System.out.println("Invalid contract type. Please try again.");
                return;
            }

            System.out.println("\nContract Summary:");
            System.out.println("Customer: " + contract.getCustomerName());
            System.out.println("Vehicle: " + contract.getVehicle().getYear() + " " +
                    contract.getVehicle().getMake() + " " +
                    contract.getVehicle().getModel());

            if (contract instanceof SalesContract) {
                SalesContract salesContract = (SalesContract) contract;
                List<AddOn> addOns = salesContract.getAddOns();

                if (!addOns.isEmpty()) {
                    System.out.println("\nSelected Add-ons:");
                    for (AddOn addOn : addOns) {
                        System.out.println("- " + addOn.getName() + ": $" +
                                String.format("%.2f", addOn.getPrice()));
                    }
                    System.out.println("Add-ons Total: $" +
                            String.format("%.2f", salesContract.getAddOnsPrice()));
                }
            }

            System.out.println("Total Price: $" + String.format("%.2f", contract.getTotalPrice()));
            System.out.println("Monthly Payment: $" + String.format("%.2f", contract.getMonthlyPayment()));

            System.out.print("\nConfirm contract (y/n): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                contractManager.saveContract(contract);

                dealership.removeVehicle(vehicle);
                fileManager.saveDealership(dealership);

                System.out.println("Contract saved successfully!");
            } else {
                System.out.println("Contract cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error processing contract: " + e.getMessage());
        }
    }

    private void offerAddOns(SalesContract contract) {
        System.out.println("\nWould you like to add any of the following add-ons to your purchase?");

        List<AddOn> availableAddOns = addOnManager.getAvailableAddOns();
        for (int i = 0; i < availableAddOns.size(); i++) {
            AddOn addOn = availableAddOns.get(i);
            System.out.println((i + 1) + ". " + addOn.getName() + " - $" +
                    String.format("%.2f", addOn.getPrice()));
            System.out.println("   " + addOn.getDescription());
        }

        System.out.println("0. Done selecting add-ons");

        boolean done = false;
        while (!done) {
            System.out.print("\nSelect an add-on (0 to finish): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                done = true;
            } else if (choice >= 1 && choice <= availableAddOns.size()) {
                AddOn selectedAddOn = availableAddOns.get(choice - 1);
                contract.addAddOn(selectedAddOn);
                System.out.println("Added: " + selectedAddOn.getName());
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        if (!contract.getAddOns().isEmpty()) {
            System.out.println("\nSelected Add-ons:");
            for (AddOn addOn : contract.getAddOns()) {
                System.out.println("- " + addOn.getName() + ": $" +
                        String.format("%.2f", addOn.getPrice()));
            }
            System.out.println("Add-ons Total: $" +
                    String.format("%.2f", contract.getAddOnsPrice()));
        }
    }

    private void processAdminRequest() {
        AdminUserInterface adminUI = new AdminUserInterface(scanner, contractManager);

        if (adminUI.authenticate()) {
            System.out.println("Authentication successful!");
            adminUI.display();
        } else {
            System.out.println("Authentication failed. Access denied.");
        }
    }
}