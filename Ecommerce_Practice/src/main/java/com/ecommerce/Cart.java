package com.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, String> products; // productId -> productName

    public Cart() {
        products = new HashMap<>();
    }

    // Method to add a product to the cart
    public boolean addProduct(int productId, String productName) {
        if (products.containsKey(productId)) {
            return false; // Product already in cart
        }
        products.put(productId, productName);
        return true; // Product added successfully
    }

    // Method to remove a product from the cart
    public boolean removeProduct(int productId) {
        if (!products.containsKey(productId)) {
            return false; // Product not found in cart
        }
        products.remove(productId);
        return true; // Product removed successfully
    }

    // Method to get all products in the cart
    public Map<Integer, String> getProducts() {
        return products;
    }

    // Method to check if the cart is empty
    public boolean isEmpty() {
        return products.isEmpty();
    }

    // Method to clear the cart
    public void clear() {
        products.clear();
    }
}
