package ru.edu.ui.models.requests;

import lombok.*;
import ru.edu.ui.models.responses.AgeLimitResponse;
import ru.edu.ui.models.responses.CountryResponse;
import ru.edu.ui.models.responses.GenreResponse;
import ru.edu.ui.models.responses.PersonResponse;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FilmRequest {
    private String title;
    private String titleForeign;
    private String description;
    private String slogan;
    private Integer yearOfProduction;
    private LocalDate releaseDateInWorld;
    private LocalDate releaseDateInRussia;
    private Integer budget;
    private Integer durationInSeconds;
    private Long ageLimitId;
    private List<Long> countriesId;
    private List<Long> genresId;
    private List<Long> directorsId;
    private List<Long> screenwritersId;
    private List<Long> producersId;
    private List<Long> actorsId;
}
