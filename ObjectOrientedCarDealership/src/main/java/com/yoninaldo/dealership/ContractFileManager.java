package com.yoninaldo.dealership;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContractFileManager {
    private String FILE_PATH = "src/main/resources/contracts.csv";

    public ContractFileManager() {
    }

    public ContractFileManager(String filePath) {
        this.FILE_PATH = filePath;
    }

    public void saveContract(Contract contract) {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Vehicle vehicle = contract.getVehicle();

            if (contract instanceof SalesContract) {
                SalesContract salesContract = (SalesContract) contract;

                StringBuilder contractLine = new StringBuilder();
                contractLine.append("SALE|")
                        .append(contract.getDate()).append("|")
                        .append(contract.getCustomerName()).append("|")
                        .append(contract.getCustomerEmail()).append("|")
                        .append(vehicle.getVin()).append("|")
                        .append(vehicle.getYear()).append("|")
                        .append(vehicle.getMake()).append("|")
                        .append(vehicle.getModel()).append("|")
                        .append(vehicle.getVehicleType()).append("|")
                        .append(vehicle.getColor()).append("|")
                        .append(vehicle.getOdometer()).append("|")
                        .append(vehicle.getPrice()).append("|")
                        .append(salesContract.getSalesTaxAmount()).append("|")
                        .append(salesContract.getRecordingFee()).append("|")
                        .append(salesContract.getProcessingFee()).append("|")
                        .append(salesContract.getTotalPrice()).append("|")
                        .append(salesContract.isFinance() ? "YES" : "NO").append("|")
                        .append(salesContract.getMonthlyPayment());

                List<AddOn> addOns = salesContract.getAddOns();
                if (!addOns.isEmpty()) {
                    contractLine.append("|");
                    for (int i = 0; i < addOns.size(); i++) {
                        AddOn addOn = addOns.get(i);
                        contractLine.append(addOn.getName()).append(",")
                                .append(addOn.getDescription()).append(",")
                                .append(addOn.getPrice());

                        if (i < addOns.size() - 1) {
                            contractLine.append(";");
                        }
                    }
                }

                bufferedWriter.write(contractLine.toString());
            } else if (contract instanceof LeaseContract) {
                LeaseContract leaseContract = (LeaseContract) contract;

                bufferedWriter.write("LEASE|" +
                        contract.getDate() + "|" +
                        contract.getCustomerName() + "|" +
                        contract.getCustomerEmail() + "|" +
                        vehicle.getVin() + "|" +
                        vehicle.getYear() + "|" +
                        vehicle.getMake() + "|" +
                        vehicle.getModel() + "|" +
                        vehicle.getVehicleType() + "|" +
                        vehicle.getColor() + "|" +
                        vehicle.getOdometer() + "|" +
                        vehicle.getPrice() + "|" +
                        leaseContract.getExpectedEndingValue() + "|" +
                        leaseContract.getLeaseFee() + "|" +
                        leaseContract.getTotalPrice() + "|" +
                        leaseContract.getMonthlyPayment()
                );
            }

            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error writing to contracts file: " + e.getMessage());
        }
    }

    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return contracts;
            }

            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Contract contract = parseContractLine(line);
                if (contract != null) {
                    contracts.add(contract);
                }
            }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            System.out.println("Error reading contracts file: " + e.getMessage());
        }

        return contracts;
    }

    private Contract parseContractLine(String line) {
        String[] parts = line.split("\\|");
        String contractType = parts[0];

        try {
            String date = parts[1];
            String customerName = parts[2];
            String customerEmail = parts[3];

            int vin = Integer.parseInt(parts[4]);
            int year = Integer.parseInt(parts[5]);
            String make = parts[6];
            String model = parts[7];
            VehicleType vehicleType = VehicleType.valueOf(parts[8]);
            String color = parts[9];
            int odometer = Integer.parseInt(parts[10]);
            double price = Double.parseDouble(parts[11]);

            Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

            if (contractType.equals("SALE")) {
                boolean finance = parts[16].equals("YES");
                SalesContract salesContract = new SalesContract(date, customerName, customerEmail, vehicle, finance);

                salesContract.setSalesTaxAmount(Double.parseDouble(parts[12]));
                salesContract.setRecordingFee(Double.parseDouble(parts[13]));
                salesContract.setProcessingFee(Double.parseDouble(parts[14]));

                if (parts.length > 18) {
                    String addOnsStr = parts[18];
                    String[] addOnEntries = addOnsStr.split(";");

                    for (String addOnEntry : addOnEntries) {
                        String[] addOnParts = addOnEntry.split(",");
                        if (addOnParts.length == 3) {
                            String name = addOnParts[0];
                            String description = addOnParts[1];
                            double addOnPrice = Double.parseDouble(addOnParts[2]);

                            AddOn addOn = new AddOn(name, description, addOnPrice);
                            salesContract.addAddOn(addOn);
                        }
                    }
                }

                return salesContract;

            } else if (contractType.equals("LEASE")) {
                LeaseContract leaseContract = new LeaseContract(date, customerName, customerEmail, vehicle);

                leaseContract.setExpectedEndingValue(Double.parseDouble(parts[12]));
                leaseContract.setLeaseFee(Double.parseDouble(parts[13]));

                return leaseContract;
            }

        } catch (Exception e) {
            System.out.println("Error parsing contract line: " + e.getMessage());
        }

        return null;
    }
}