package com.delicious.pos.enums;

public enum DrinkFlavor {
    COCA_COLA("Coca-Cola"),
    PEPSI("Pepsi"),
    SPRITE("Sprite"),
    DR_PEPPER("Dr Pepper"),
    LEMONADE("Lemonade"),
    ORANGE_JUICE("Orange Juice"),
    ICED_TEA("Iced Tea"),
    WATER("Water");

    private final String displayName;

    DrinkFlavor(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}