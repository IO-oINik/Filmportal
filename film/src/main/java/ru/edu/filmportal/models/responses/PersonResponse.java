package ru.edu.filmportal.models.responses;

import java.time.LocalDate;

public record PersonResponse(
        long id,
        String name,
        String surname,
        String nameForeign,
        LocalDate dateOfBirth,
        CountryResponse countryOfBirth
) {
}
