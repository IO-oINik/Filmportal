package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotNull;

public record AgeLimitRequest(
        @NotNull(message = "Age limit is required")
        Integer age
) {
}
