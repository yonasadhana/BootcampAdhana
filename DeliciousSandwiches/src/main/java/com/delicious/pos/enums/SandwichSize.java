package com.delicious.pos.enums;

public enum SandwichSize {
    FOUR_INCH("4\"", 5.50),
    EIGHT_INCH("8\"", 7.00),
    TWELVE_INCH("12\"", 8.50);

    private final String displayName;
    private final double basePrice;

    SandwichSize(String displayName, double basePrice) {
        this.displayName = displayName;
        this.basePrice = basePrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getBasePrice() {
        return basePrice;
    }
}