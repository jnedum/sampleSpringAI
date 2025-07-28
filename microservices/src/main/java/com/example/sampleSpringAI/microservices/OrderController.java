package com.example.sampleSpringAI.microservices;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    @PostConstruct
    private void populateOrders() {
        Long id = 0L;
        Random random = new Random();
        OrderStatus[] statuses = OrderStatus.values();

        //generate a date from one month ago untiil now
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);
        ZoneId zoneId = ZoneId.systemDefault(); // Or choose a specific zone like ZoneId.of("America/New_York")
        long startMillis = oneMonthAgo.atZone(zoneId).toInstant().toEpochMilli();
        long endMillis = now.atZone(zoneId).toInstant().toEpochMilli();

        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setId(++id);
            order.setCustomerId(id);
            order.setStatus(statuses[random.nextInt(statuses.length)]);
            order.setItems(new ArrayList<OrderItem>());

            // Generate a random date since last month
            long randomMillis = random.nextLong(startMillis, endMillis + 1);
            order.setOrderEntryDate( LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(randomMillis), zoneId) );

            orders.put(order.getId(), order);
        }
    }

    @Tool(description = "Creates and order, given a customer ID")
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        long id = idCounter.incrementAndGet();
        order.setId(id);
        order.setCustomerId(order.getCustomerId());
        order.setStatus(OrderStatus.PENDING);
        orders.put(id, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @Tool(description = "Gets an order given its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orders.get(id);
        return (order != null)
                ? ResponseEntity.ok(order)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Tool(description = "Gets all orders")
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(new ArrayList<>(orders.values()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Order existing = orders.get(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedOrder.setId(id);
        orders.put(id, updatedOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orders.remove(id) != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Order order = orders.get(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        order.setStatus(status);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Order> addItemToOrder(@PathVariable Long id, @RequestBody OrderItem item) {
        Order order = orders.get(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        order.getItems().add(item);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}/items/{sku}")
    public ResponseEntity<Order> removeItemFromOrder(@PathVariable Long id, @PathVariable String sku) {
        Order order = orders.get(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        order.getItems().removeIf(item -> item.getProductId().equals(Long.getLong(sku)));
        return ResponseEntity.ok(order);
    }
}

