package ru.edu.filmportal.services;

import jakarta.persistence.EntityExistsException;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.BadRequestException;
import ru.edu.filmportal.exceptions.NotFoundException;
import ru.edu.filmportal.mappers.FilmScoreMapper;
import ru.edu.filmportal.models.database.FilmScore;
import ru.edu.filmportal.models.responses.FilmResponse;
import ru.edu.filmportal.models.responses.FilmScoreResponse;
import ru.edu.filmportal.models.requests.FilmScoreRequest;
import ru.edu.filmportal.models.responses.UserResponse;
import ru.edu.filmportal.repositories.FilmScoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmScoreService {
    private final UserService userService;
    private final FilmService filmService;
    private final FilmScoreRepository repository;
    private final FilmScoreMapper mapper;

    public float averageScore(long filmId) {
        filmService.findById(filmId);

        List<FilmScore> filmScores = repository.findAllByFilmId(filmId);
        float sum = 0;
        for (FilmScore filmScore : filmScores) {
            sum += filmScore.getScore();
        }
        if (!filmScores.isEmpty()) {
            return sum / filmScores.size();
        } else {
            return 0;
        }
    }

    public List<FilmScoreResponse> findAll() {
        return repository.findAll().stream().map(mapper::toFilmScoreResponse).toList();
    }

    public FilmScoreResponse findById(long id) {
        return repository.findById(id).map(mapper::toFilmScoreResponse).orElseThrow(() -> new NotFoundException("Film score not found with the ID: " + id));
    }

    public FilmScoreResponse findByUserIdAndFilmId(long userId, long filmId) {
        return mapper.toFilmScoreResponse(repository.findByUserIdAndFilmId(userId, filmId).orElseThrow(()->new NotFoundException(String.format("Film score not found with the filmID: %d and userID: %d", filmId, userId))));
    }

    public List<FilmScoreResponse> findByUserId(long userId) {
        return repository.findAllByUserId(userId).stream().map(mapper::toFilmScoreResponse).toList();
    }

    public List<FilmScoreResponse> findByFilmId(long filmId) {
        try {
            filmService.findById(filmId);
        } catch (NotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
        return repository.findAllByFilmId(filmId).stream().map(mapper::toFilmScoreResponse).toList();
    }

    public FilmScoreResponse create(FilmScoreRequest request) {
        UserResponse userResponse = userService.findMe();

        if(repository.existsByFilmIdAndUserId(request.filmId(), userResponse.id())) {
            throw new EntityExistsException(String.format("Film score already exists with the filmID: %d and userID: %d", request.filmId(), userResponse.id()));
        }

        try {
            filmService.findById(request.filmId());
        } catch (NotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }

        var filmScore = mapper.toFilmScore(request, userResponse.id());
        filmScore = repository.save(filmScore);
        return mapper.toFilmScoreResponse(filmScore);
    }

    public void delete(long id) {
        UserResponse userResponse = userService.findMe();
        if (userResponse.role().equals("ADMIN")) {
            repository.deleteById(id);
            return;
        }

        repository.findById(id).ifPresent(filmScore -> {
            if (filmScore.getUserId().equals(userResponse.id())) {
                repository.delete(filmScore);
            } else {
                throw new ForbiddenException("You do not have permission to delete this review");
            }
        });
    }
}
