package backend.spring_ai.infrastructure.http.dto.request;

import backend.spring_ai.application.input.PersistTransactionInput;
import backend.spring_ai.domain.Category;

public record TransactionRequest(String description, long amount, Category category) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description, amount, category);
    }
}
