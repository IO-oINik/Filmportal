package ru.edu.ui.models.responses;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FilmResponse {
    private Long id;
    private String title;
    private String titleForeign;
    private String description;
    private String slogan;
    private Integer yearOfProduction;
    private LocalDate releaseDateInWorld;
    private LocalDate releaseDateInRussia;
    private Integer budget;
    private Integer durationInSeconds;
    private AgeLimitResponse ageLimit;
    private Set<CountryResponse> countries;
    private Set<GenreResponse> genres;
    private Set<PersonResponse> directors;
    private Set<PersonResponse> screenwriters;
    private Set<PersonResponse> producers;
    private Set<PersonResponse> actors;
}
