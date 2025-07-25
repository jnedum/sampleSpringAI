package com.example.sampleSpringAI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.clone()
                .build();
    }

    @GetMapping("/chat")
    public Joke chat() {
        return chatClient
                .prompt()
                .user("tell me a joke")
                .call()
                .entity(Joke.class)
                ;
    }
}
