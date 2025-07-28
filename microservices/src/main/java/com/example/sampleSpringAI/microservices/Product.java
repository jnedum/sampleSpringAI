package com.example.sampleSpringAI.microservices;

public record Product(
        Long id,
        String name,
        String description,
        double price
) {
}