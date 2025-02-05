package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.requests.FilmScoreRequest;
import ru.edu.filmportal.models.responses.FilmScoreResponse;
import ru.edu.filmportal.services.FilmScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/film-score")
@RequiredArgsConstructor
public class FilmScoreController {
    private final FilmScoreService filmScoreService;

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает оценку фильма по id")
    public ResponseEntity<FilmScoreResponse> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(filmScoreService.findById(id));
    }

    @GetMapping("/{id}/average")
    @Operation(summary = "Возвращает среднюю оценку фильма по id")
    public ResponseEntity<Float> getAverage(@PathVariable("id") long id) {
        return ResponseEntity.ok(filmScoreService.averageScore(id));
    }

    @GetMapping("/find")
    @Operation(summary = "Возвращает все оценки фильма по id фильма")
    public ResponseEntity<List<FilmScoreResponse>> getByFilmId(@RequestParam Long filmId) {
        return ResponseEntity.ok(filmScoreService.findByFilmId(filmId));
    }

    @PostMapping("/create")
    @Operation(summary = "Создание новой оценки фильма")
    public ResponseEntity<FilmScoreResponse> create(@Valid @RequestBody FilmScoreRequest request) {
        return ResponseEntity.ok(filmScoreService.create(request));
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Удаление оценки фильма по id")
    public void delete(@PathVariable("id") long id) {
        filmScoreService.delete(id);
    }
}
