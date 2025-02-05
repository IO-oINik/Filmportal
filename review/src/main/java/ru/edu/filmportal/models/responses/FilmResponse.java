package ru.edu.filmportal.models.responses;

import java.time.LocalDate;
import java.util.Set;

public record FilmResponse (
    Long id,
    String title,
    String titleForeign,
    String description,
    String slogan,
    Integer yearOfProduction,
    LocalDate releaseDateInWorld,
    LocalDate releaseDateInRussia,
    Integer budget,
    Integer durationInSeconds,
    AgeLimitResponse ageLimit,
    Set<CountryResponse> countries,
    Set<GenreResponse> genres,
    Set<PersonResponse> directors,
    Set<PersonResponse> screenwriters,
    Set<PersonResponse> producers,
    Set<PersonResponse> actors
) {}