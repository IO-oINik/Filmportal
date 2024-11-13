package ru.edu.filmportal.mappers;

import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.Genre;
import ru.edu.filmportal.models.requests.GenreRequest;
import ru.edu.filmportal.models.responses.GenreResponse;

@Component
public class GenreMapper {
    public GenreResponse toGenreResponse(Genre genre) {
        return new GenreResponse(genre.getId(), genre.getTitle());
    }

    public Genre toGenre(GenreRequest genreRequest) {
        return new Genre(genreRequest.title());
    }
}
