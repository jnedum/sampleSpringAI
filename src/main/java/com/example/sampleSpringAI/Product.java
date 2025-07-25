package com.example.sampleSpringAI;

public record Product(
        Long id,
        String name,
        String description,
        double price
) {
}