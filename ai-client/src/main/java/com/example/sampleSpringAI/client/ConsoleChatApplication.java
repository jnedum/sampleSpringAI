package com.example.sampleSpringAI.client;

import io.modelcontextprotocol.client.McpSyncClient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleChatApplication implements CommandLineRunner {
    private final ChatClient chatClient;

    public ConsoleChatApplication(ChatClient.Builder builder, List<McpSyncClient> mcpSyncClients) {
        this.chatClient = builder
                .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
                .build();
    }

    @Override
    public void run(String... args) throws Exception {
        var scanner = new Scanner(System.in);
        System.out.println("\nLet's chat!");
        while (true) {
            System.out.print("\nUSER: ");
            System.out.println("ASSISTANT: " +
                    chatClient
                            .prompt()
                            .user((scanner.nextLine()))
                            .call()
                            .content()
            );
        }
    }
}
