package com.delicious.pos.builders;

import com.delicious.pos.enums.*;
import com.delicious.pos.models.*;

import java.util.ArrayList;
import java.util.List;

public class SandwichBuilder {
    private BreadType bread;
    private SandwichSize size;
    private List<Meat> meats;
    private List<Cheese> cheeses;
    private List<Vegetable> vegetables;
    private List<Sauce> sauces;
    private List<Side> sides;
    private boolean toasted;

    public SandwichBuilder() {
        reset();
    }

    public void reset() {
        this.bread = null;
        this.size = null;
        this.meats = new ArrayList<>();
        this.cheeses = new ArrayList<>();
        this.vegetables = new ArrayList<>();
        this.sauces = new ArrayList<>();
        this.sides = new ArrayList<>();
        this.toasted = false;
    }

    public SandwichBuilder setBread(BreadType bread) {
        this.bread = bread;
        return this;
    }

    public SandwichBuilder setSize(SandwichSize size) {
        this.size = size;
        return this;
    }

    public SandwichBuilder addMeat(Meat meat) {
        this.meats.add(meat);
        return this;
    }

    public SandwichBuilder addCheese(Cheese cheese) {
        this.cheeses.add(cheese);
        return this;
    }

    public SandwichBuilder addVegetable(Vegetable vegetable) {
        this.vegetables.add(vegetable);
        return this;
    }

    public SandwichBuilder addSauce(Sauce sauce) {
        this.sauces.add(sauce);
        return this;
    }

    public SandwichBuilder addSide(Side side) {
        this.sides.add(side);
        return this;
    }

    public SandwichBuilder setToasted(boolean toasted) {
        this.toasted = toasted;
        return this;
    }

    public void createBLT() {
        reset();
        this.bread = BreadType.WHITE;
        this.size = SandwichSize.EIGHT_INCH;
        this.meats.add(new Meat(MeatType.BACON, false));
        this.cheeses.add(new Cheese(CheeseType.CHEDDAR, false));
        this.vegetables.add(new Vegetable(VegetableType.LETTUCE));
        this.vegetables.add(new Vegetable(VegetableType.TOMATOES));
        this.sauces.add(new Sauce(SauceType.RANCH));
        this.toasted = true;
    }

    public void createPhillyCheesesteak() {
        reset();
        this.bread = BreadType.WHITE;
        this.size = SandwichSize.EIGHT_INCH;
        this.meats.add(new Meat(MeatType.STEAK, false));
        this.cheeses.add(new Cheese(CheeseType.AMERICAN, false));
        this.vegetables.add(new Vegetable(VegetableType.PEPPERS));
        this.sauces.add(new Sauce(SauceType.MAYO));
        this.toasted = true;
    }

    public Sandwich build() {
        if (bread == null || size == null) {
            throw new IllegalStateException("Bread and size must be selected before building sandwich");
        }

        Sandwich sandwich = new Sandwich(bread, size);

        for (Meat meat : meats) {
            sandwich.addTopping(meat);
        }
        for (Cheese cheese : cheeses) {
            sandwich.addTopping(cheese);
        }
        for (Vegetable vegetable : vegetables) {
            sandwich.addTopping(vegetable);
        }
        for (Sauce sauce : sauces) {
            sandwich.addTopping(sauce);
        }
        for (Side side : sides) {
            sandwich.addTopping(side);
        }

        sandwich.setToasted(toasted);

        reset();
        return sandwich;
    }
}