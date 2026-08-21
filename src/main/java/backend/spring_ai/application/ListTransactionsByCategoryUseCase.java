package backend.spring_ai.application;

import backend.spring_ai.application.input.PersistTransactionInput;
import backend.spring_ai.application.output.TransactionOutput;
import backend.spring_ai.domain.Category;
import backend.spring_ai.domain.Transaction;
import backend.spring_ai.domain.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionsByCategoryUseCase {
    private final TransactionRepository transactionRepository;

    public ListTransactionsByCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionOutput> execute(Category category) {
        return transactionRepository.findAllByCategory(category)
                .stream().map(TransactionOutput::from).toList();
    }
}
