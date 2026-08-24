package backend.spring_ai.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Transaction {
    private TransactionId id;
    private String description;
    private long amount;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Timestamp
    private LocalDateTime createdAt;

    public Transaction(String description, long amount, Category category) {
        this.id = new TransactionId();
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public Transaction(TransactionId id, String description, long amount, Category category) {
        this.id = new TransactionId();
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
}
