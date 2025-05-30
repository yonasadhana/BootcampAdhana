package com.delicious.pos.models;

import com.delicious.pos.enums.DrinkFlavor;
import com.delicious.pos.enums.DrinkSize;

public class Drink implements OrderItem {
    private final DrinkSize size;
    private final DrinkFlavor flavor;

    public Drink(DrinkSize size, DrinkFlavor flavor) {
        this.size = size;
        this.flavor = flavor;
    }

    @Override
    public double getPrice() {
        return size.getPrice();
    }

    @Override
    public String getDescription() {
        return size.getDisplayName() + " " + flavor.getDisplayName();
    }

    @Override
    public String getDetailedDescription() {
        return getDescription();
    }

    public DrinkSize getSize() {
        return size;
    }

    public DrinkFlavor getFlavor() {
        return flavor;
    }
}