package com.delicious.pos.models;

public class Receipt {
    private final Order order;
    private final String fileName;
    private final String content;

    public Receipt(Order order) {
        this.order = order;
        this.fileName = order.getReceiptFileName();
        this.content = order.getDetailedReceipt();
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public Order getOrder() {
        return order;
    }
}