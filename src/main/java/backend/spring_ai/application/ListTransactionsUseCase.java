package backend.spring_ai.application;

import backend.spring_ai.application.output.TransactionOutput;
import backend.spring_ai.domain.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionsUseCase {
    private final TransactionRepository transactionRepository;

    public ListTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionOutput> execute() {
        return transactionRepository.findAll().stream().map(TransactionOutput::from).toList();
    }
}
