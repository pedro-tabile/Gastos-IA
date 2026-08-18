package backend.spring_ai;

import com.google.genai.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GENAI_API_KEY", matches = ".+")
public class GenAiChatModelIT {
    /* Também é possível somente fazer a injeção das classes (como Client e GoogleGenAiChatModel) e configurar opções
    no .properties */

    // Client da API para comunicação
    @Autowired
    Client genAiClient;

    @Test
    void should_receiveResponse_when_chatModelIsCalled() {
        // Opções para o chat model
        var options = GoogleGenAiChatOptions.builder()
                .model("gemini-3.6-flash")
                .temperature(0.8)
                .responseMimeType("text/plain")
                .build();

        // Implementação do LLM
        var chatModel = GoogleGenAiChatModel.builder()
                .genAiClient(genAiClient)
                .defaultOptions(options)
                .build();

        var response = chatModel.call("Gere um registro de budgeting, com descrição de gasto, valor em reais e local");

        // Asserção para a saída (response) - ela não deve estar vazia
        assertThat(response).isNotEmpty();

        System.out.println(response);
    }
}
