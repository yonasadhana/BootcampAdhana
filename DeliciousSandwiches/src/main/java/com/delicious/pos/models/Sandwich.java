package com.delicious.pos.models;

import com.delicious.pos.enums.BreadType;
import com.delicious.pos.enums.SandwichSize;

import java.util.ArrayList;
import java.util.List;

public class Sandwich implements OrderItem {
    private final BreadType bread;
    private final SandwichSize size;
    private final List<Topping> toppings;
    private boolean toasted;

    public Sandwich(BreadType bread, SandwichSize size) {
        this.bread = bread;
        this.size = size;
        this.toppings = new ArrayList<>();
        this.toasted = false;
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }

    @Override
    public double getPrice() {
        double totalPrice = size.getBasePrice();

        for (Topping topping : toppings) {
            if (topping instanceof PremiumTopping) {
                totalPrice += ((PremiumTopping) topping).getPrice(size);
            }
        }

        return totalPrice;
    }

    @Override
    public String getDescription() {
        return size.getDisplayName() + " " + bread.getDisplayName() + " sandwich" +
                (toasted ? " (toasted)" : "");
    }

    @Override
    public String getDetailedDescription() {
        StringBuilder details = new StringBuilder();
        details.append(getDescription()).append("\n");

        List<String> meatList = new ArrayList<>();
        List<String> cheeseList = new ArrayList<>();
        List<String> vegList = new ArrayList<>();
        List<String> sauceList = new ArrayList<>();
        List<String> sideList = new ArrayList<>();

        for (Topping topping : toppings) {
            String toppingDesc = topping.getDescription();
            if (topping instanceof Meat) {
                meatList.add(toppingDesc);
            } else if (topping instanceof Cheese) {
                cheeseList.add(toppingDesc);
            } else if (topping instanceof Vegetable) {
                vegList.add(toppingDesc);
            } else if (topping instanceof Sauce) {
                sauceList.add(toppingDesc);
            } else if (topping instanceof Side) {
                sideList.add(toppingDesc);
            }
        }

        if (!meatList.isEmpty()) {
            details.append("  Meats: ").append(String.join(", ", meatList)).append("\n");
        }
        if (!cheeseList.isEmpty()) {
            details.append("  Cheese: ").append(String.join(", ", cheeseList)).append("\n");
        }
        if (!vegList.isEmpty()) {
            details.append("  Vegetables: ").append(String.join(", ", vegList)).append("\n");
        }
        if (!sauceList.isEmpty()) {
            details.append("  Sauces: ").append(String.join(", ", sauceList)).append("\n");
        }
        if (!sideList.isEmpty()) {
            details.append("  Sides: ").append(String.join(", ", sideList)).append("\n");
        }

        return details.toString().trim();
    }

    public BreadType getBread() {
        return bread;
    }

    public SandwichSize getSize() {
        return size;
    }

    public List<Topping> getToppings() {
        return new ArrayList<>(toppings);
    }

    public boolean isToasted() {
        return toasted;
    }
}