package com.delicious.pos.models;

public abstract class RegularTopping extends Topping {

    public RegularTopping(String name) {
        super(name);
    }

    @Override
    public String getDescription() {
        return name;
    }
}