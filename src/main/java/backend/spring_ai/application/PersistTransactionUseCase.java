package backend.spring_ai.application;

import backend.spring_ai.application.input.PersistTransactionInput;
import backend.spring_ai.application.output.TransactionOutput;
import backend.spring_ai.domain.Transaction;
import backend.spring_ai.domain.TransactionRepository;

public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionOutput execute(PersistTransactionInput input) {
        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.amount(), input.category())
        );

        return TransactionOutput.from(transaction);
    }
}
