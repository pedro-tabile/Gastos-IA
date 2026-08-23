package backend.spring_ai.application.output;

import backend.spring_ai.domain.Category;
import backend.spring_ai.domain.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record TransactionOutput(String id, String description, double value, Category category, LocalDateTime createdAt) {
    public static TransactionOutput from(Transaction transaction) {
        return new TransactionOutput(
                transaction.getId().id().toString(),
                transaction.getDescription(),
                BigDecimal.valueOf(transaction.getAmount()).setScale(2, RoundingMode.HALF_EVEN).doubleValue(),
                transaction.getCategory(),
                transaction.getCreatedAt()
        );
    }
}
