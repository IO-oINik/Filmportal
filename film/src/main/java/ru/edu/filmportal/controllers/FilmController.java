package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.requests.FilmCreateRequest;
import ru.edu.filmportal.models.requests.FilmEditRequest;
import ru.edu.filmportal.models.responses.FilmResponse;
import ru.edu.filmportal.models.responses.MessageResponse;
import ru.edu.filmportal.models.responses.PageableDataResponse;
import ru.edu.filmportal.services.FilmService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/film")
@Tag(name = "Film Controller")
public class FilmController {
    private final FilmService service;

    @GetMapping("/all")
    @Operation(summary = "Возвращает все фильмы постранично используя параметры")
    public ResponseEntity<PageableDataResponse<FilmResponse>> getAllByTitle(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order) {
        return ResponseEntity.ok(service.findAllWithParameters(page, pageSize, sort, order));
    }

    @GetMapping("/{film-id}")
    @Operation(summary = "Возвращает фильм по id")
    public ResponseEntity<FilmResponse> getById(@PathVariable("film-id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Создаёт новый фильм")
    public ResponseEntity<FilmResponse> create(@RequestBody @Valid FilmCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{film-id}/edit")
    @Operation(summary = "Редактирует фильмы по id")
    public ResponseEntity<FilmResponse> edit(@PathVariable("film-id") long id, @RequestBody @Valid FilmEditRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PostMapping("/{film-id}/edit/add-country")
    @Operation(summary = "Добавить страну фильму по id")
    public ResponseEntity<FilmResponse> addCountry(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idCountry is required") Long idCountry) {
        return ResponseEntity.ok(service.addCountry(id, idCountry));
    }

    @PostMapping("/{film-id}/edit/delete-country")
    @Operation(summary = "Удалить страну фильму по id")
    public ResponseEntity<FilmResponse> deleteCountry(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idCountry is required") Long idCountry) {
        return ResponseEntity.ok(service.deleteCountry(id, idCountry));
    }

    @PostMapping("/{film-id}/edit/add-genre")
    @Operation(summary = "Добавить жанр фильму по id")
    public ResponseEntity<FilmResponse> addGenre(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idGenre is required") Long idGenre) {
        return ResponseEntity.ok(service.addGenre(id, idGenre));
    }

    @PostMapping("/{film-id}/edit/delete-genre")
    @Operation(summary = "Удалить жанр фильму по id")
    public ResponseEntity<FilmResponse> deleteGenre(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idGenre is required") Long idGenre) {
        return ResponseEntity.ok(service.deleteGenre(id, idGenre));
    }

    @PostMapping("/{film-id}/edit/add-director")
    @Operation(summary = "Добавить режисёра фильму по id")
    public ResponseEntity<FilmResponse> addDirector(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idDirector is required") Long idDirector) {
        return ResponseEntity.ok(service.addDirector(id, idDirector));
    }

    @PostMapping("/{film-id}/edit/delete-director")
    @Operation(summary = "Удалить режисёра фильму по id")
    public ResponseEntity<FilmResponse> deleteDirector(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idDirector is required") Long idDirector) {
        return ResponseEntity.ok(service.deleteDirector(id, idDirector));
    }

    @PostMapping("/{film-id}/edit/add-screenwriter")
    @Operation(summary = "Добавить сценариста фильму по id")
    public ResponseEntity<FilmResponse> addScreenwriter(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idScreenwriter is required") Long idScreenwriter) {
        return ResponseEntity.ok(service.addScreenwriter(id, idScreenwriter));
    }

    @PostMapping("/{film-id}/edit/delete-screenwriter")
    @Operation(summary = "Удалить сценариста фильму по id")
    public ResponseEntity<FilmResponse> deleteScreenwriter(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idScreenwriter is required") Long idScreenwriter) {
        return ResponseEntity.ok(service.deleteScreenwriter(id, idScreenwriter));
    }

    @PostMapping("/{film-id}/edit/add-producer")
    @Operation(summary = "Добавить продюсера фильму по id")
    public ResponseEntity<FilmResponse> addProducer(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idProducer is required") Long idProducer) {
        return ResponseEntity.ok(service.addProducer(id, idProducer));
    }

    @PostMapping("/{film-id}/edit/delete-producer")
    @Operation(summary = "Удалить продюсера фильму по id")
    public ResponseEntity<FilmResponse> deleteProducer(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idProducer is required") Long idProducer) {
        return ResponseEntity.ok(service.deleteProducer(id, idProducer));
    }

    @PostMapping("/{film-id}/edit/add-actor")
    @Operation(summary = "Добавить актёра фильму по id")
    public ResponseEntity<FilmResponse> addActor(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idActor is required") Long idActor) {
        return ResponseEntity.ok(service.addActor(id, idActor));
    }

    @PostMapping("/{film-id}/edit/delete-actor")
    @Operation(summary = "Удалить актёра фильму по id")
    public ResponseEntity<FilmResponse> deleteActor(@PathVariable("film-id") long id, @RequestBody @NotNull(message = "idActor is required") Long idActor) {
        return ResponseEntity.ok(service.deleteActor(id, idActor));
    }

    @DeleteMapping("/{film-id}/delete")
    @Operation(summary = "Удалить фильм по id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("film-id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Film has been deleted"));
    }
}
