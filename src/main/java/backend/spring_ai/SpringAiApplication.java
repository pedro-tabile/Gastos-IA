package backend.spring_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiApplication {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClient) {
        return chatClient.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAiApplication.class, args);
    }

}
