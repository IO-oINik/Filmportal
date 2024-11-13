package ru.edu.filmportal.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.Film;
import ru.edu.filmportal.models.requests.FilmCreateRequest;
import ru.edu.filmportal.models.responses.FilmResponse;

@Component
@RequiredArgsConstructor
public class FilmMapper {
    private final AgeLimitMapper mapperAgeLimit;
    private final CountryMapper mapperCountry;
    private final GenreMapper mapperGenre;
    private final PersonMapper mapperPerson;

    public FilmResponse toFilmResponse(Film film) {
        return new FilmResponse(
                film.getId(),
                film.getTitle(),
                film.getTitleForeign(),
                film.getDescription(),
                film.getSlogan(),
                film.getYearOfProduction(),
                film.getReleaseDateInWorld(),
                film.getReleaseDateInRussia(),
                film.getBudget(),
                film.getDurationInSeconds(),
                mapperAgeLimit.toAgeLimitResponse(film.getAgeLimit()),
                film.getCountries().stream().map(mapperCountry::toCountryResponse).toList(),
                film.getGenres().stream().map(mapperGenre::toGenreResponse).toList(),
                film.getDirectors().stream().map(mapperPerson::toPersonResponse).toList(),
                film.getScreenwriters().stream().map(mapperPerson::toPersonResponse).toList(),
                film.getProducers().stream().map(mapperPerson::toPersonResponse).toList(),
                film.getActors().stream().map(mapperPerson::toPersonResponse).toList()
        );
    }

    public Film toFilm(FilmCreateRequest filmRequest) {
        return Film.builder()
                .title(filmRequest.title())
                .titleForeign(filmRequest.titleForeign())
                .description(filmRequest.description())
                .slogan(filmRequest.slogan())
                .yearOfProduction(filmRequest.yearOfProduction())
                .releaseDateInWorld(filmRequest.releaseDateInWorld())
                .releaseDateInRussia(filmRequest.releaseDateInRussia())
                .budget(filmRequest.budget())
                .durationInSeconds(filmRequest.durationInSeconds())
                .build();
    }
}
