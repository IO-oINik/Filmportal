package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record PersonCreateRequest (
        @NotNull(message = "Person name is required")
        @NotBlank(message = "Person name can not blank")
        @NotEmpty(message = "Person name can not empty")
        String name,
        @NotNull(message = "Person surname is required")
        @NotBlank(message = "Person surname can not blank")
        @NotEmpty(message = "Person surname can not empty")
        String surname,
        String nameForeign,
        LocalDate dateOfBirth,
        Long idCountry
){
}