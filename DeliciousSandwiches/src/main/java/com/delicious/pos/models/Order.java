package com.delicious.pos.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> items;
    private final LocalDateTime orderTime;
    private final String customerName;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public Order(String customerName) {
        this.items = new ArrayList<>();
        this.orderTime = LocalDateTime.now();
        this.customerName = customerName;
    }

    public void addItem(OrderItem item) {
        items.add(0, item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();
    }

    public String getOrderSummary() {
        if (items.isEmpty()) {
            return "No items in order";
        }

        StringBuilder summary = new StringBuilder();
        for (OrderItem item : items) {
            summary.append(item.getDescription())
                    .append(" - $")
                    .append(String.format("%.2f", item.getPrice()))
                    .append("\n");
        }
        return summary.toString();
    }

    public String getDetailedReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("=".repeat(50)).append("\n");
        receipt.append("      YONINALDO'S DELICIOUS SANDWICHES\n");
        receipt.append("=".repeat(50)).append("\n");
        receipt.append("Customer: ").append(customerName).append("\n");
        receipt.append("Order Date: ").append(orderTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        receipt.append("-".repeat(50)).append("\n\n");

        for (OrderItem item : items) {
            receipt.append(item.getDetailedDescription()).append("\n");
            receipt.append("Price: $").append(String.format("%.2f", item.getPrice())).append("\n\n");
        }

        receipt.append("-".repeat(50)).append("\n");
        receipt.append("TOTAL: $").append(String.format("%.2f", getTotal())).append("\n");
        receipt.append("=".repeat(50)).append("\n");
        receipt.append("\nThank you for your order, ").append(customerName).append("!\n");
        receipt.append("We hope you enjoy your meal!\n");

        return receipt.toString();
    }

    public String getReceiptFileName() {
        return orderTime.format(TIME_FORMATTER) + ".txt";
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public OrderItem removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.remove(index);
        }
        throw new IndexOutOfBoundsException("Invalid item index");
    }

    public String getCustomerName() {
        return customerName;
    }
}