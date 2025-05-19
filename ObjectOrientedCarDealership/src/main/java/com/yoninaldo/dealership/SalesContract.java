package com.yoninaldo.dealership;

import java.util.ArrayList;
import java.util.List;

public class SalesContract extends Contract {
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean finance;
    private List<AddOn> addOns;

    public SalesContract(String date, String customerName, String customerEmail,
                         Vehicle vehicle, boolean finance) {
        super(date, customerName, customerEmail, vehicle);
        this.salesTaxAmount = vehicle.getPrice() * 0.05;
        this.recordingFee = 100.0;
        this.processingFee = vehicle.getPrice() < 10000 ? 295.0 : 495.0;
        this.finance = finance;
        this.addOns = new ArrayList<>();
    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(double salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinance() {
        return finance;
    }

    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    public List<AddOn> getAddOns() {
        return new ArrayList<>(addOns);
    }

    public void addAddOn(AddOn addOn) {
        this.addOns.add(addOn);
    }

    public double getAddOnsPrice() {
        double total = 0.0;
        for (AddOn addOn : addOns) {
            total += addOn.getPrice();
        }
        return total;
    }

    @Override
    public double getTotalPrice() {
        return getVehicle().getPrice() + salesTaxAmount + recordingFee + processingFee + getAddOnsPrice();
    }

    @Override
    public double getMonthlyPayment() {
        if (!finance) {
            return 0.0;
        }

        double price = getTotalPrice();
        int months;
        double interestRate;

        if (price >= 10000) {
            months = 48;
            interestRate = 0.0425;
        } else {
            months = 24;
            interestRate = 0.0525;
        }

        double monthlyInterestRate = interestRate / 12;
        return price * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, months)) /
                (Math.pow(1 + monthlyInterestRate, months) - 1);
    }
}