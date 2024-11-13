package ru.edu.filmportal.models.responses;

public record ReviewResponse(
        Long id,
        String title,
        String text,
        TypeReviewResponse type,
        Long userId,
        Long filmId
) {
}
