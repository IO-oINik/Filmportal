package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotNull;

public record AgeLimitRequest(
        @NotNull(message = "Country age is required")
        Integer age
) {
}
