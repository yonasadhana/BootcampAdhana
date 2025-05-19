package com.yoninaldo.dealership;

import java.util.ArrayList;
import java.util.List;

public class AddOnManager {
    private final List<AddOn> availableAddOns;

    public AddOnManager() {
        this.availableAddOns = new ArrayList<>();
        initializeAddOns();
    }

    private void initializeAddOns() {
        availableAddOns.add(new AddOn("Nitrogen Tires", "Filled with nitrogen for better pressure retention", 199.99));
        availableAddOns.add(new AddOn("Window Tinting", "Premium window tinting for UV protection", 299.99));
        availableAddOns.add(new AddOn("All-season Floor Mats", "Heavy-duty protection for your car's interior", 129.99));
        availableAddOns.add(new AddOn("Splash Guards", "Protect your vehicle from road debris", 89.99));
        availableAddOns.add(new AddOn("Cargo Tray", "Waterproof protection for your trunk", 79.99));
        availableAddOns.add(new AddOn("Wheel Locks", "Security for your wheels against theft", 69.99));
    }

    public List<AddOn> getAvailableAddOns() {
        return new ArrayList<>(availableAddOns);
    }

    public AddOn getAddOnByIndex(int index) {
        if (index >= 0 && index < availableAddOns.size()) {
            return availableAddOns.get(index);
        }
        return null;
    }
}