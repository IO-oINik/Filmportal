package ru.edu.filmportal.models.response;

public record TokensResponse(
        String refreshToken,
        String accessToken,
        Long expiresAt
) {
}
