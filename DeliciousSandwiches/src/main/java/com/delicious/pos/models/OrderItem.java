package com.delicious.pos.models;

public interface OrderItem {
    double getPrice();
    String getDescription();
    String getDetailedDescription();
}