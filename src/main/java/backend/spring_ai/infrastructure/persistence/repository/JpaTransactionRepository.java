package backend.spring_ai.infrastructure.persistence.repository;

import backend.spring_ai.domain.Category;
import backend.spring_ai.domain.Transaction;
import backend.spring_ai.domain.TransactionId;
import backend.spring_ai.domain.TransactionRepository;
import backend.spring_ai.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
public class JpaTransactionRepository implements TransactionRepository {
    private final TransactionEntityRepository transactionEntityRepository;

    public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction findById(UUID id) {
        return transactionEntityRepository.findById(id).get().toTransaction();
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

    @Override
    public List<Transaction> findAll() {
        return StreamSupport.stream(transactionEntityRepository.findAll().spliterator(), false)
                .map(TransactionEntity::toTransaction).toList();
    }

    @Override
    public void delete(UUID id) {
        transactionEntityRepository.deleteById(id);
    }

}
