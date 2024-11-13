package ru.edu.filmportal.models.responses;

public record FilmScoreResponse(
        Long userId,
        Long filmId,
        Integer score
) {
}
