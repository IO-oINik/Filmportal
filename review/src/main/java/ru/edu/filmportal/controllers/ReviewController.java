package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.requests.ReviewCreateRequest;
import ru.edu.filmportal.models.responses.ReviewResponse;
import ru.edu.filmportal.services.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает отзыв о фильме")
    public ResponseEntity<ReviewResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @GetMapping("/find")
    @Operation(summary = "Возвращает все отзывы о фильме по id фильма")
    public ResponseEntity<List<ReviewResponse>> getByFilmId(@RequestParam Long filmId) {
        return ResponseEntity.ok(reviewService.findByFilmId(filmId));
    }

    @PostMapping("/create")
    @Operation(summary = "Создание отзыва о фильме")
    public ResponseEntity<ReviewResponse> create(@Valid @RequestBody ReviewCreateRequest request) {
        return ResponseEntity.ok(reviewService.create(request));
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Удаление отзыва о фильме")
    public void delete(@PathVariable("id") long id) {
        reviewService.deleteById(id);
    }
}
