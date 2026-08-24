package backend.spring_ai.application.input;

import backend.spring_ai.domain.Category;

public record TransactionInput(String description, long amount, Category category) {
}
