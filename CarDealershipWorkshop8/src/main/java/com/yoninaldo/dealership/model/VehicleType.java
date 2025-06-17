package com.yoninaldo.dealership.model;

public enum VehicleType {
    CAR, TRUCK, SUV, VAN;

    public static VehicleType fromString(String typeString) {
        try {
            return valueOf(typeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CAR;
        }
    }
}