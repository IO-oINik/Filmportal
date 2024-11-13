package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FilmScoreRequest(
        @NotNull(message = "userID is required")
        Long userId,
        @NotNull(message = "filmId is required")
        Long filmId,
        @NotNull(message = "score is required")
        @Max(message = "score should be no more than 10", value = 10)
        @Min(message = "score must be at least 0", value = 0)
        Integer score
) {
}
