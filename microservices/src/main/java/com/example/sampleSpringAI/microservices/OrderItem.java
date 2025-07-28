package com.example.sampleSpringAI.microservices;

public class OrderItem {
        Long productId;
        int quantity;
        double priceAtPurchase;
        double totalPrice;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) { this.productId = productId; }

    public void setProduct(Product product, int quantity) {
        this.productId = product.id();
        this.quantity = quantity;
        this.priceAtPurchase = product.price();
        this.totalPrice = priceAtPurchase * quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() { return totalPrice; }

    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}