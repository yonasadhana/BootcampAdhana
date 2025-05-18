package com.yoninaldo.dealership;

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