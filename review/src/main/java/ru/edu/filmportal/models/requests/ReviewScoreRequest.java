package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotNull;

public record ReviewScoreRequest(
        @NotNull(message = "userID is required")
        Long userId,
        @NotNull(message = "reviewId is required")
        Long reviewId
) {
}
