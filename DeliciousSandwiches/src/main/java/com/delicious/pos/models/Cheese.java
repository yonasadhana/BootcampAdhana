package com.delicious.pos.models;

import com.delicious.pos.enums.CheeseType;
import com.delicious.pos.enums.SandwichSize;

public class Cheese extends PremiumTopping {
    private final CheeseType cheeseType;

    public Cheese(CheeseType cheeseType, boolean isExtra) {
        super(cheeseType.getDisplayName(), isExtra);
        this.cheeseType = cheeseType;
    }

    @Override
    public double getPrice(SandwichSize size) {
        double basePrice = switch (size) {
            case FOUR_INCH -> 0.75;
            case EIGHT_INCH -> 1.50;
            case TWELVE_INCH -> 2.25;
        };

        if (isExtra) {
            double extraPrice = switch (size) {
                case FOUR_INCH -> 0.30;
                case EIGHT_INCH -> 0.60;
                case TWELVE_INCH -> 0.90;
            };
            return basePrice + extraPrice;
        }

        return basePrice;
    }

    public CheeseType getCheeseType() {
        return cheeseType;
    }
}