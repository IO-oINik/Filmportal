package ru.edu.filmportal.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.requests.FilmCreateRequest;
import ru.edu.filmportal.models.requests.FilmEditRequest;
import ru.edu.filmportal.models.responses.FilmResponse;
import ru.edu.filmportal.models.responses.MessageResponse;
import ru.edu.filmportal.services.FilmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/film")
public class FilmController {
    private final FilmService service;

    @GetMapping("/all")
    public ResponseEntity<List<FilmResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/all/{page-number}")
    public ResponseEntity<List<FilmResponse>> getAll(@PathVariable("page-number") int numberList) {
        return ResponseEntity.ok(service.findAll(numberList+1));
    }

    @GetMapping("/{film-id}")
    public ResponseEntity<FilmResponse> getById(@PathVariable("film-id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<FilmResponse> create(@RequestBody @Validated FilmCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{film-id}/edit")
    public ResponseEntity<FilmResponse> edit(@PathVariable("film-id") long id, @RequestBody @Validated FilmEditRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PostMapping("/{film-id}/edit/add-country")
    public ResponseEntity<FilmResponse> addCountry(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idCountry is required") Long idCountry) {
        return ResponseEntity.ok(service.addCountry(id, idCountry));
    }

    @PostMapping("/{film-id}/edit/delete-country")
    public ResponseEntity<FilmResponse> deleteCountry(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idCountry is required") Long idCountry) {
        return ResponseEntity.ok(service.deleteCountry(id, idCountry));
    }

    @PostMapping("/{film-id}/edit/add-genre")
    public ResponseEntity<FilmResponse> addGenre(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idGenre is required") Long idGenre) {
        return ResponseEntity.ok(service.addGenre(id, idGenre));
    }

    @PostMapping("/{film-id}/edit/delete-genre")
    public ResponseEntity<FilmResponse> deleteGenre(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idGenre is required") Long idGenre) {
        return ResponseEntity.ok(service.deleteGenre(id, idGenre));
    }

    @PostMapping("/{film-id}/edit/add-director")
    public ResponseEntity<FilmResponse> addDirector(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idDirector is required") Long idDirector) {
        return ResponseEntity.ok(service.addDirector(id, idDirector));
    }

    @PostMapping("/{film-id}/edit/delete-director")
    public ResponseEntity<FilmResponse> deleteDirector(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idDirector is required") Long idDirector) {
        return ResponseEntity.ok(service.deleteDirector(id, idDirector));
    }

    @PostMapping("/{film-id}/edit/add-screenwriter")
    public ResponseEntity<FilmResponse> addScreenwriter(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idScreenwriter is required") Long idScreenwriter) {
        return ResponseEntity.ok(service.addScreenwriter(id, idScreenwriter));
    }

    @PostMapping("/{film-id}/edit/delete-screenwriter")
    public ResponseEntity<FilmResponse> deleteScreenwriter(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idScreenwriter is required") Long idScreenwriter) {
        return ResponseEntity.ok(service.deleteScreenwriter(id, idScreenwriter));
    }

    @PostMapping("/{film-id}/edit/add-producer")
    public ResponseEntity<FilmResponse> addProducer(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idProducer is required") Long idProducer) {
        return ResponseEntity.ok(service.addProducer(id, idProducer));
    }

    @PostMapping("/{film-id}/edit/delete-producer")
    public ResponseEntity<FilmResponse> deleteProducer(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idProducer is required") Long idProducer) {
        return ResponseEntity.ok(service.deleteProducer(id, idProducer));
    }

    @PostMapping("/{film-id}/edit/add-actor")
    public ResponseEntity<FilmResponse> addActor(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idActor is required") Long idActor) {
        return ResponseEntity.ok(service.addActor(id, idActor));
    }

    @PostMapping("/{film-id}/edit/delete-actor")
    public ResponseEntity<FilmResponse> deleteActor(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idActor is required") Long idActor) {
        return ResponseEntity.ok(service.deleteActor(id, idActor));
    }

    @DeleteMapping("/{film-id}/delete")
    public ResponseEntity<MessageResponse> delete(@PathVariable("film-id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Film has been deleted"));
    }
}
