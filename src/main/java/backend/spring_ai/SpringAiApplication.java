package backend.spring_ai;

import backend.spring_ai.application.ListTransactionsByCategoryUseCase;
import backend.spring_ai.application.PersistTransactionUseCase;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class SpringAiApplication {
    private String contexto = """
            Você é um assitente financeiro e tem como tarefa a extração de dados de transações para armazena-las no
            banco de dados, contendo ID, descrição, valor e categoria do gasto. Ao ajustar os dados escolha a categoria 
            que mais se adequa ao fornecido. Por fim, informe somente a descrição, valor, data de criação (somente data 
            e horário no formato dd/mm/aaaa - hh:mm) e categoria do gasto, além da operação realizada. Retorne tudo em PT BR.
          """;

    @Bean
    ChatClient chatClient(GoogleGenAiChatModel googleGenAiChatModel,
                          PersistTransactionUseCase persistTransactionUseCase,
                          ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase
    ) {
        return ChatClient.builder(googleGenAiChatModel)
                .defaultSystem(contexto)
                .defaultTools(persistTransactionUseCase, listTransactionsByCategoryUseCase)
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAiApplication.class, args);
    }

}
