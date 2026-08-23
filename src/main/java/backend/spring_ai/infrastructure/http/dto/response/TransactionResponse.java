package backend.spring_ai.infrastructure.http.dto.response;

import backend.spring_ai.application.output.TransactionOutput;
import backend.spring_ai.domain.Category;

import java.time.LocalDateTime;

public record TransactionResponse(String id, String description, double amount, Category category, LocalDateTime createdAt) {
    public static TransactionResponse from(TransactionOutput output) {
        return new TransactionResponse(output.id(), output.description(), output.value(), output.category(), output.createdAt());
    }
}
