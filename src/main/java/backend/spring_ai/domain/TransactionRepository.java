package backend.spring_ai.domain;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findAllByCategory(Category category);
    List<Transaction> findAll();
    void delete(UUID id);
    Transaction findById(UUID id);
}
