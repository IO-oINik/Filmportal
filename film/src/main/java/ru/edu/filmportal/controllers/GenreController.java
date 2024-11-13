package ru.edu.filmportal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.requests.GenreRequest;
import ru.edu.filmportal.models.responses.GenreResponse;
import ru.edu.filmportal.models.responses.MessageResponse;
import ru.edu.filmportal.services.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService service;

    @GetMapping("/all")
    public ResponseEntity<List<GenreResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{genre-id}")
    public ResponseEntity<GenreResponse> getById(@PathVariable("genre-id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<GenreResponse> create(@RequestBody @Validated GenreRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{genre-id}/edit")
    public ResponseEntity<GenreResponse> edit(@PathVariable("genre-id") long id, @RequestBody @Validated GenreRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{genre-id}/delete")
    public ResponseEntity<MessageResponse> delete(@PathVariable("genre-id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Genre has been deleted"));
    }
}
