package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GenreRequest (
        @NotNull(message = "Genre title is required")
        @NotBlank(message = "Genre title can not blank")
        @NotEmpty(message = "Genre title can not empty")
        String title
) {
}
