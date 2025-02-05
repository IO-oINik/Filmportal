package ru.edu.filmportal.models.responses;

public record FilmScoreResponse(
        Long id,
        Long userId,
        Long filmId,
        Integer score
) {
}
