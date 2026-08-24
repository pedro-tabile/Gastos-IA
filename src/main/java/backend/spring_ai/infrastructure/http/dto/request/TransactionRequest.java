package backend.spring_ai.infrastructure.http.dto.request;

import backend.spring_ai.application.input.TransactionInput;
import backend.spring_ai.domain.Category;

public record TransactionRequest(String description, long amount, Category category) {
    public TransactionInput toInput() {
        return new TransactionInput(description, amount, category);
    }
}
