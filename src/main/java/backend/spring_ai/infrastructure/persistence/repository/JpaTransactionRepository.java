package backend.spring_ai.infrastructure.persistence.repository;

import backend.spring_ai.domain.Category;
import backend.spring_ai.domain.Transaction;
import backend.spring_ai.domain.TransactionRepository;
import backend.spring_ai.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaTransactionRepository implements TransactionRepository {
    private final TransactionEntityRepository transactionEntityRepository;

    public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = TransactionEntity.from(transaction);
        return transactionEntityRepository.save(entity).toTransaction();
    }

    @Override
    public List<Transaction> findAllByCategory(Category category) {
        return transactionEntityRepository.findAllByCategory(category).stream()
                .map(TransactionEntity::toTransaction).toList();
    }
}
