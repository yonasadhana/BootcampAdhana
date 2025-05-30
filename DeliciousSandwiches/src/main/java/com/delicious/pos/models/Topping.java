package com.delicious.pos.models;

public abstract class Topping {
    protected final String name;

    public Topping(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getDescription();
}