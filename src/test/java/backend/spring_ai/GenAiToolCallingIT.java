package backend.spring_ai;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GENAI_API_KEY", matches = ".+")
public class GenAiToolCallingIT {
    @Autowired
    GoogleGenAiChatModel googleGenAiChatModel;

    static class MathTools {
        @Tool(description = "soma de dois números inteiros")
        public int sum(int a, int b) {
            return a+b;
        }

        @Tool(description = "subtração de dois números inteiros")
        public int diff(int a, int b) {
            return a-b;
        }
    }

    @Test
    void should_executeSum_when_prompted(){
        var chatClient = ChatClient.builder(googleGenAiChatModel)
                .defaultTools(new MathTools())
                .defaultSystem("Você é um matemático")
                .build();

        // .prompt() constrói o um prompt para ser enviado à API
        var response = chatClient.prompt("Some 10 mais 20, depois substraia 30 do resultado anterior e exiba somente o resultado.")
                .call().content();

        assertThat(response).contains("0");
        System.out.println(response);
    }
}
