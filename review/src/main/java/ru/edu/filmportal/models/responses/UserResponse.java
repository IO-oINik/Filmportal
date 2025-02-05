package ru.edu.filmportal.models.responses;

public record UserResponse (
    Long id,
    String name,
    String surname,
    String nickname,
    String email,
    String role
) {}
