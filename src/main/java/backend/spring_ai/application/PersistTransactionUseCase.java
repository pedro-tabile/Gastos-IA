package backend.spring_ai.application;

import backend.spring_ai.application.input.TransactionInput;
import backend.spring_ai.application.output.TransactionOutput;
import backend.spring_ai.domain.Transaction;
import backend.spring_ai.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "persist-transaction", description = "Persiste uma nova transação")
    public TransactionOutput execute(
            @ToolParam(description = "Dados de entrada para persistência: descrição, valor e categoria da transação")
            TransactionInput input
    ) {
        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.amount(), input.category())
        );

        return TransactionOutput.from(transaction);
    }
}
