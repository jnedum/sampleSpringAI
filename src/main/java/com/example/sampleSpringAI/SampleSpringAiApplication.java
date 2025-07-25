package com.example.sampleSpringAI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@SpringBootApplication
public class SampleSpringAiApplication {

	private final ChatClient chatClient;

	public SampleSpringAiApplication(ChatClient.Builder builder) {
		this.chatClient = builder.clone()
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleSpringAiApplication.class, args);
	}
}