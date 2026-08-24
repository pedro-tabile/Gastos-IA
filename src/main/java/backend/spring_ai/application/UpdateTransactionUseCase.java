package backend.spring_ai.application;

import backend.spring_ai.application.input.TransactionInput;
import backend.spring_ai.application.output.TransactionOutput;
import backend.spring_ai.domain.TransactionId;
import backend.spring_ai.domain.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public UpdateTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionOutput execute(TransactionId id, TransactionInput input) {
        var transaction = transactionRepository.findById(id.id());
        transaction.setAmount(input.amount());
        transaction.setCategory(input.category());
        transaction.setDescription(input.description());

        var saved = transactionRepository.save(transaction);
        return TransactionOutput.from(saved);
    }
}
