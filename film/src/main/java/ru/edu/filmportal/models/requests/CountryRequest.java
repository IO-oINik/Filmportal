package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CountryRequest (
        @NotNull(message = "Country title is required")
        @NotBlank(message = "Country title can not blank")
        @NotEmpty(message = "Country title can not empty")
        String title
) {
}
