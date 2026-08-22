package backend.spring_ai.application;

import backend.spring_ai.application.output.TransactionOutput;
import backend.spring_ai.domain.Category;
import backend.spring_ai.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionsByCategoryUseCase {
    private final TransactionRepository transactionRepository;

    public ListTransactionsByCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "list-transactions", description = "Lista as transações financeiras registradas agrupadas por categoria")
    public List<TransactionOutput> execute(@ToolParam(description = "Categoria da transação") Category category) {
        return transactionRepository.findAllByCategory(category)
                .stream().map(TransactionOutput::from).toList();
    }
}
