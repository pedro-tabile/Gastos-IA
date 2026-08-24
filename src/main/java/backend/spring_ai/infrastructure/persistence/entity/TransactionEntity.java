package backend.spring_ai.infrastructure.persistence.entity;

import backend.spring_ai.domain.Category;
import backend.spring_ai.domain.Transaction;
import backend.spring_ai.domain.TransactionId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    private UUID id;
    private String description;
    private long amount;
    private Category category;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public TransactionEntity(UUID id, String description, long amount, Category category) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public static TransactionEntity from(Transaction transaction) {
        return new TransactionEntity(
                transaction.getId().id(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getCreatedAt()
        );
    }

    public Transaction toTransaction(){
        return new Transaction(
                new TransactionId(this.id),
                this.description,
                this.amount,
                this.category,
                this.createdAt
        );
    }
}
