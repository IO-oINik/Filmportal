package ru.edu.filmportal.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.mappers.FilmScoreMapper;
import ru.edu.filmportal.models.database.FilmScore;
import ru.edu.filmportal.models.responses.FilmScoreResponse;
import ru.edu.filmportal.models.requests.FilmScoreRequest;
import ru.edu.filmportal.repositories.FilmScoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmScoreService {

    private final FilmScoreRepository repository;
    private final FilmScoreMapper mapper;

    public float averageScore(long filmId) {
        List<FilmScore> filmScores = repository.findAllByFilmId(filmId);
        float sum = 0;
        for (FilmScore filmScore : filmScores) {
            sum += filmScore.getScore();
        }
        return sum / filmScores.size();
    }

    public List<FilmScoreResponse> findAll() {
        return repository.findAll().stream().map(mapper::toFilmScoreResponse).toList();
    }

    public FilmScoreResponse findById(long id) {
        return repository.findById(id).map(mapper::toFilmScoreResponse).orElseThrow(() -> new EntityNotFoundException("Film score not found with the ID: " + id));
    }

    public FilmScoreResponse findByUserIdAndFilmId(long userId, long filmId) {
        return mapper.toFilmScoreResponse(repository.findByUserIdAndFilmId(userId, filmId).orElseThrow(()->new EntityNotFoundException(String.format("Film score not found with the filmID: %d and userID: %d", filmId, userId))));
    }

    public List<FilmScoreResponse> findByUserId(long userId) {
        return repository.findAllByUserId(userId).stream().map(mapper::toFilmScoreResponse).toList();
    }

    public List<FilmScoreResponse> findByFilmId(long filmId) {
        return repository.findAllByFilmId(filmId).stream().map(mapper::toFilmScoreResponse).toList();
    }

    public FilmScoreResponse create(FilmScoreRequest request) {
        if(repository.existsByFilmIdAndUserId(request.filmId(), request.userId())) {
            throw new EntityExistsException(String.format("Film score already exists with the filmID: %d and userID: %d", request.filmId(), request.userId()));
        }
        var filmScore = mapper.toFilmScore(request);
        filmScore = repository.save(filmScore);
        return mapper.toFilmScoreResponse(filmScore);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
