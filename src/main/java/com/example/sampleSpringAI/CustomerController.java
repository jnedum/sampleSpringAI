package com.example.sampleSpringAI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        long id = idCounter.incrementAndGet();
        Customer newCustomer = new Customer(id, customer.name(), customer.email());
        customers.put(id, newCustomer);
        return ResponseEntity.status(201).body(newCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = customers.get(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(new ArrayList<>(customers.values()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        if (!customers.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        Customer customer = new Customer(id, updatedCustomer.name(), updatedCustomer.email());
        customers.put(id, customer);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        return customers.remove(id) != null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

