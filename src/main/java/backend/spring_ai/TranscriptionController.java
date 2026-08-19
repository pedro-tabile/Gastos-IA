package backend.spring_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TranscriptionController {
    // Starter configura e injeta
    private final ChatClient chatClient;

    public TranscriptionController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat-client")
    // Param da req inferido pelo param do método
    String chat(String prompt) {
        return chatClient.prompt().user(prompt).call().content() ;
    }
}
