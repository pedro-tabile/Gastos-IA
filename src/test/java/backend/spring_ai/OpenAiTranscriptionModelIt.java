package backend.spring_ai;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GROQ_API_KEY", matches = ".+")
public class OpenAiTranscriptionModelIt {
    @Autowired
    OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    // Permite parâmetros
    @ParameterizedTest
    @CsvSource({
            "eletrodomestico.ogg, 800",
            "padaria.ogg, 3",
            "pintor.ogg, 75",
    })
    void should_containExpectedKeywords_when_audioFilesAreProcessed(String fileName, String expectedKeywords){
        var recording = new ClassPathResource("audio/" + fileName);

        /*var prompt = "Áudio em português (Brasil) e descreve gastos financeiros. \\\n" +
                "Contém também valor em reais, definição de estabelecimento e ações (compra, gastos...)";
        var options = OpenAiAudioTranscriptionOptions.builder()
                .model("whisper-large-v3-turbo")
                .temperature(0f)
                .language("pt")
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .prompt(prompt)
                .build();
        var transcriptionPrompt = new AudioTranscriptionPrompt(recording, options);*/

        var response = openAiAudioTranscriptionModel.call(recording);

        assertThat(response).contains(expectedKeywords);
        System.out.println(response);
    }
}
