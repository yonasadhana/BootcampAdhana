package com.delicious.pos.models;

import com.delicious.pos.enums.ChipType;

public class Chips implements OrderItem {
    private final ChipType type;
    private static final double PRICE = 1.50;

    public Chips(ChipType type) {
        this.type = type;
    }

    @Override
    public double getPrice() {
        return PRICE;
    }

    @Override
    public String getDescription() {
        return type.getDisplayName();
    }

    @Override
    public String getDetailedDescription() {
        return getDescription();
    }

    public ChipType getType() {
        return type;
    }
}