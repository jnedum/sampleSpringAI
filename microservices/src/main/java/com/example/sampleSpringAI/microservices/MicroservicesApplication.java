package com.example.sampleSpringAI.microservices;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroservicesApplication {

    @Bean
    ToolCallbackProvider toolCallbackProvider(OrderController orderController) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(orderController)
                .build();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(MicroservicesApplication.class, args);
    }
}
