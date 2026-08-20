package backend.spring_ai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.elevenlabs.ElevenLabsTextToSpeechModel;
import org.springframework.ai.elevenlabs.ElevenLabsTextToSpeechOptions;
import org.springframework.ai.elevenlabs.api.ElevenLabsApi;
import org.springframework.ai.elevenlabs.api.ElevenLabsVoicesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ElevenLabsAiSpeechModelIt {
    @Autowired
    ElevenLabsTextToSpeechModel elevenLabsTextToSpeechModel;

    @Test
    void should_produceAudio_when_textIsProvided() throws IOException {
        ElevenLabsTextToSpeechOptions speechOptions = ElevenLabsTextToSpeechOptions.builder()
                .model("eleven_v3")
                .voiceId("e3yjeWnwuGsHvboTqMyB")
                .outputFormat(ElevenLabsApi.OutputFormat.MP3_22050_32.getValue())
                .build();

        TextToSpeechPrompt prompt = new TextToSpeechPrompt("O valor total do serviço ficou em 80 reais. " +
                "Posso confirmar o pagamento?");
        TextToSpeechResponse response = elevenLabsTextToSpeechModel.call(prompt);
        byte[] outputGenerated = response.getResult().getOutput();

        assertThat(outputGenerated).hasSizeGreaterThan(1024);

        // Cria um arquivo temporário vazio
        var tempFile = Files.createTempFile("AUDIO_", ".mp3");
        // Escreve bytes em um file
        Files.write(tempFile, outputGenerated);

        System.out.println(tempFile.toAbsolutePath());
    }
}
