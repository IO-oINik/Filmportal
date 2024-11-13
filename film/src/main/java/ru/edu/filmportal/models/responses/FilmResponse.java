package ru.edu.filmportal.models.responses;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
        List<CountryResponse> countries,
        List<GenreResponse> genres,
        List<PersonResponse> directors,
        List<PersonResponse> screenwriters,
        List<PersonResponse> producers,
        List<PersonResponse> actors
)
{ }
