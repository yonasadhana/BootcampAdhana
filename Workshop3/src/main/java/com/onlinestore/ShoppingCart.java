package com.onlinestore;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }


    public void addProductToCart(Product product) {
        products.add(product);
        System.out.println(product.getProductName() + " has been added to your cart.");
    }


    public void removeProductFromCart(String sku) {
        Product productToRemove = null;


        for (Product product : products) {
            if (product.getSku().equals(sku)) {
                productToRemove = product;
                break;
            }
        }


        if (productToRemove != null) {
            products.remove(productToRemove);
            System.out.println(productToRemove.getProductName() + " has been removed from your cart.");
        } else {
            System.out.println("Product with SKU " + sku + " was not found in your cart.");
        }
    }


    public double getCartTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }


    public List<Product> getProducts() {
        return products;
    }


    public void clearCart() {
        products.clear();
        System.out.println("Your cart has been cleared.");
    }
}