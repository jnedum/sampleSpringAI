package com.example.sampleSpringAI.microservices;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Order {
    private Long id;
    private Long customerId;
    private LocalDateTime orderEntryDate;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;
    private Double finalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM-dd-yyyy hh:mm:ss")
    public LocalDateTime getOrderEntryDate() { return orderEntryDate; }

    public void setOrderEntryDate(LocalDateTime orderEntryDate) { this.orderEntryDate = orderEntryDate;}

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getFinalPrice() { return finalPrice; }

    public void setFinalPrice(Double finalPrice) { this.finalPrice = finalPrice; }
}