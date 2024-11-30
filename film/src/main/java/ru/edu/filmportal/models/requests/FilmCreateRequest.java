package ru.edu.filmportal.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record FilmCreateRequest(
    @NotNull(message = "Film title is required")
    @NotBlank(message = "Film title can not blank")
    @NotEmpty(message = "Film title can not empty")
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
    List<Long> actorsId){ }
