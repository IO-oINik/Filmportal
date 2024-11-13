package ru.edu.filmportal.models.requests;

import java.time.LocalDate;

public record PersonEditRequest(
        String name,
        String surname,
        String nameForeign,
        LocalDate dateOfBirth,
        Long idCountry
) {
}
