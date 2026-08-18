package backend.spring_ai;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GENAI_API_KEY", matches = ".+")
public class GenAiChatClientIT {
    @Autowired
    GoogleGenAiChatModel googleGenAiChatModel;

    @Test
    void should_executeSum_when_prompted(){
        var chatClient = ChatClient.builder(googleGenAiChatModel)
                .defaultSystem("Você é um matemático")
                .build();

        // .prompt() constrói o um prompt para ser enviado à API
        var response = chatClient.prompt("Some 10 mais 20, depois substraia 30 do resultado anterior e exiba somente o resultado.")
                .call().content();

        assertThat(response).contains("0");
        System.out.println(response);
    }
}
