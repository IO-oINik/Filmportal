package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ReviewCreateRequest(
        @NotNull(message = "Title is required")
        @NotBlank(message = "Title can not blank")
        @NotEmpty(message = "Title can not empty")
        String title,
        @NotNull(message = "Text is required")
        @NotBlank(message = "Text can not blank")
        @NotEmpty(message = "Text can not empty")
        String text,
        @NotNull(message = "typeId is required")
        Long typeId,
        @NotNull(message = "userId is required")
        Long userId,
        @NotNull(message = "filmId is required")
        Long filmId
) {
}
