package com.delicious.pos.models;

import com.delicious.pos.enums.VegetableType;

public class Vegetable extends RegularTopping {
    private final VegetableType vegetableType;

    public Vegetable(VegetableType vegetableType) {
        super(vegetableType.getDisplayName());
        this.vegetableType = vegetableType;
    }

    public VegetableType getVegetableType() {
        return vegetableType;
    }
}