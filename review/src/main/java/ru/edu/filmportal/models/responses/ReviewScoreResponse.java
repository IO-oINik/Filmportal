package ru.edu.filmportal.models.responses;

public record ReviewScoreResponse(
        Long id,
        Long userId,
        Long reviewId,
        Boolean assessment
) {
}
