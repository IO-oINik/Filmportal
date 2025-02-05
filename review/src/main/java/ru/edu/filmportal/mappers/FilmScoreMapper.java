package ru.edu.filmportal.mappers;

import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.FilmScore;
import ru.edu.filmportal.models.requests.FilmScoreRequest;
import ru.edu.filmportal.models.responses.FilmScoreResponse;

@Component
public class FilmScoreMapper {
    public FilmScoreResponse toFilmScoreResponse(FilmScore filmScore) {
        return new FilmScoreResponse(filmScore.getId(), filmScore.getUserId(), filmScore.getFilmId(), filmScore.getScore());
    }

    public FilmScore toFilmScore(FilmScoreRequest filmScoreRequest, long userId) {
        return new FilmScore(userId, filmScoreRequest.filmId(), filmScoreRequest.score());
    }
}
