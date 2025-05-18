package com.yoninaldo.dealership;

import java.io.*;

public class DealershipFileManager {

    private String FILE_PATH = "src/main/resources/inventory.csv";

    public DealershipFileManager() {
    }

    public DealershipFileManager(String filePath) {
        this.FILE_PATH = filePath;
    }

    public Dealership getDealership() {
        Dealership dealership = null;

        try {
            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String dealershipLine = bufferedReader.readLine();
            if (dealershipLine != null) {
                String[] dealershipInfo = dealershipLine.split("\\|");

                if (dealershipInfo.length == 3) {
                    String name = dealershipInfo[0];
                    String address = dealershipInfo[1];
                    String phone = dealershipInfo[2];

                    dealership = new Dealership(name, address, phone);

                    String vehicleLine;
                    while ((vehicleLine = bufferedReader.readLine()) != null) {
                        Vehicle vehicle = parseVehicleLine(vehicleLine);
                        if (vehicle != null) {
                            dealership.addVehicle(vehicle);
                        }
                    }
                } else {
                    System.out.println("Dealership header line is malformed.");
                }
            }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            System.out.println("Error reading dealership file: " + e.getMessage());
        }

        return dealership;
    }

    private Vehicle parseVehicleLine(String vehicleLine) {
        String[] vehicleInfo = vehicleLine.split("\\|");

        if (vehicleInfo.length >= 8) {
            try {
                int vin = Integer.parseInt(vehicleInfo[0].trim());
                int year = Integer.parseInt(vehicleInfo[1].trim());
                String make = vehicleInfo[2].trim();
                String model = vehicleInfo[3].trim();
                VehicleType vehicleType = parseVehicleType(vehicleInfo[4].trim());
                String color = vehicleInfo[5].trim();
                int odometer = Integer.parseInt(vehicleInfo[6].trim());
                double price = Double.parseDouble(vehicleInfo[7].trim());

                return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing vehicle data: " + e.getMessage());
            }
        }

        return null;
    }

    private VehicleType parseVehicleType(String typeString) {
        switch (typeString.toLowerCase()) {
            case "car":
                return VehicleType.CAR;
            case "truck":
                return VehicleType.TRUCK;
            case "suv":
                return VehicleType.SUV;
            case "van":
                return VehicleType.VAN;
            default:
                return VehicleType.CAR;
        }
    }

    public void saveDealership(Dealership dealership) {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(dealership.getName() + "|" +
                    dealership.getAddress() + "|" +
                    dealership.getPhone());
            bufferedWriter.newLine();

            for (Vehicle vehicle : dealership.getAllVehicles()) {
                bufferedWriter.write(
                        vehicle.getVin() + "|" +
                                vehicle.getYear() + "|" +
                                vehicle.getMake() + "|" +
                                vehicle.getModel() + "|" +
                                vehicle.getVehicleType() + "|" +
                                vehicle.getColor() + "|" +
                                vehicle.getOdometer() + "|" +
                                vehicle.getPrice()
                );
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error writing to dealership file: " + e.getMessage());
        }
    }
}