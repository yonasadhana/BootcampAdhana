package com.delicious.pos.enums;

public enum SideType {
    AU_JUS("Au Jus"),
    SAUCE("Sauce");

    private final String displayName;

    SideType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}