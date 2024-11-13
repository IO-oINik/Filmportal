package ru.edu.filmportal.models.requests;


import java.time.LocalDate;
import java.util.List;

public record FilmEditRequest(
        String title,
        String titleForeign,
        String description,
        String slogan,
        Integer yearOfProduction,
        LocalDate releaseDateInWorld,
        LocalDate releaseDateInRussia,
        Integer budget,
        Integer durationInSeconds,
        Long ageLimitId,
        List<Long> countriesId,
        List<Long> genresId,
        List<Long> directorsId,
        List<Long> screenwritersId,
        List<Long> producersId,
        List<Long> actorsId)
{ }
