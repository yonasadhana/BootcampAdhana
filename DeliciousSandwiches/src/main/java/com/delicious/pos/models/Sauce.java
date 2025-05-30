package com.delicious.pos.models;

import com.delicious.pos.enums.SauceType;

public class Sauce extends RegularTopping {
    private final SauceType sauceType;

    public Sauce(SauceType sauceType) {
        super(sauceType.getDisplayName());
        this.sauceType = sauceType;
    }

    public SauceType getSauceType() {
        return sauceType;
    }
}