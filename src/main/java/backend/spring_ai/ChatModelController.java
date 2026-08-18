package backend.spring_ai;

import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatModelController {
    // Starter configura e injeta
    private final GoogleGenAiChatModel googleGenAiChatModel;

    public ChatModelController(GoogleGenAiChatModel googleGenAiChatModel) {
        this.googleGenAiChatModel = googleGenAiChatModel;
    }

    @GetMapping("/chat-model")
    // Param da req inferido pelo param do método
    String chat(String prompt) {
        return googleGenAiChatModel.call(prompt);
    }
}
