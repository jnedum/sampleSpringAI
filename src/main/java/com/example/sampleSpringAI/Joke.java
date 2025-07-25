package com.example.sampleSpringAI;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record Joke(
        @JsonPropertyDescription("This is the setup for the joke")
        String setup,
        @JsonPropertyDescription("This is the punchline of the joke")
        String punchline) { }
