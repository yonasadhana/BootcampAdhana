package com.delicious.pos.enums;

public enum ChipType {
    PLAIN("Plain"),
    BBQ("BBQ"),
    SALT_VINEGAR("Salt & Vinegar"),
    SOUR_CREAM_ONION("Sour Cream & Onion"),
    CHEDDAR("Cheddar"),
    JALAPENO("Jalapeño");

    private final String displayName;

    ChipType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}