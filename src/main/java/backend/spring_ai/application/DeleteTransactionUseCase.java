package backend.spring_ai.application;

import backend.spring_ai.domain.TransactionId;
import backend.spring_ai.domain.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public DeleteTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void execute(TransactionId id) {
        transactionRepository.delete(id.id());
    }
}
