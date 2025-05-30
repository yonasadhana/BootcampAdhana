package com.delicious.pos.models;

import com.delicious.pos.enums.SandwichSize;

public abstract class PremiumTopping extends Topping {
    protected final boolean isExtra;

    public PremiumTopping(String name, boolean isExtra) {
        super(name);
        this.isExtra = isExtra;
    }

    public abstract double getPrice(SandwichSize size);

    @Override
    public String getDescription() {
        return name + (isExtra ? " (extra)" : "");
    }

    public boolean isExtra() {
        return isExtra;
    }
}