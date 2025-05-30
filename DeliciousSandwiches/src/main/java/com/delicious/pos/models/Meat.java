package com.delicious.pos.models;

import com.delicious.pos.enums.MeatType;
import com.delicious.pos.enums.SandwichSize;

public class Meat extends PremiumTopping {
    private final MeatType meatType;

    public Meat(MeatType meatType, boolean isExtra) {
        super(meatType.getDisplayName(), isExtra);
        this.meatType = meatType;
    }

    @Override
    public double getPrice(SandwichSize size) {
        double basePrice = switch (size) {
            case FOUR_INCH -> 1.00;
            case EIGHT_INCH -> 2.00;
            case TWELVE_INCH -> 3.00;
        };

        if (isExtra) {
            double extraPrice = switch (size) {
                case FOUR_INCH -> 0.50;
                case EIGHT_INCH -> 1.00;
                case TWELVE_INCH -> 1.50;
            };
            return basePrice + extraPrice;
        }

        return basePrice;
    }

    public MeatType getMeatType() {
        return meatType;
    }
}