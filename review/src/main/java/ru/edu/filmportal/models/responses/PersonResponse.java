package ru.edu.filmportal.models.responses;

import java.time.LocalDate;

public record PersonResponse (
    Long id,
    String name,
    String surname,
    String nameForeign,
    LocalDate dateOfBirth,
    CountryResponse countryOfBirth
) {}
