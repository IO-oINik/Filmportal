package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TypeReviewRequest(
        @NotNull(message = "Type is required")
        @NotBlank(message = "Type can not blank")
        @NotEmpty(message = "Type can not empty")
        String type
) {
}
