package com.yoninaldo.dealership.model;

public class LeaseContract extends Contract {
    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
        this.expectedEndingValue = vehicle.getPrice() * 0.5;
        this.leaseFee = vehicle.getPrice() * 0.07;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public void setExpectedEndingValue(double expectedEndingValue) {
        this.expectedEndingValue = expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public void setLeaseFee(double leaseFee) {
        this.leaseFee = leaseFee;
    }

    @Override
    public double getTotalPrice() {
        return getVehicle().getPrice() - expectedEndingValue + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        double price = getTotalPrice();
        int months = 36;
        double interestRate = 0.04;

        double monthlyInterestRate = interestRate / 12;
        return price * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, months)) /
                (Math.pow(1 + monthlyInterestRate, months) - 1);
    }
}