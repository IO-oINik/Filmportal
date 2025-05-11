package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ParseUrlRequest(
        @NotNull(message = "url is required")
        @NotBlank(message = "url can not blank")
        @NotEmpty(message = "url can not empty")
        String url
) {
}
