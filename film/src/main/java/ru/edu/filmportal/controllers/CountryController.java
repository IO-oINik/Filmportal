package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.requests.CountryRequest;
import ru.edu.filmportal.models.responses.CountryResponse;
import ru.edu.filmportal.models.responses.MessageResponse;
import ru.edu.filmportal.services.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
@Tag(name = "Country Controller")
public class CountryController {
    private final CountryService service;

    @GetMapping("/all")
    @Operation(summary = "Возвращает все страны")
    public ResponseEntity<List<CountryResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{country-id}")
    @Operation(summary = "Возвращает страну по id")
    public ResponseEntity<CountryResponse> getById(@PathVariable("country-id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Создание новой страны")
    public ResponseEntity<CountryResponse> create(@RequestBody @Validated CountryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{country-id}/edit")
    @Operation(summary = "Изменяет страну по id")
    public ResponseEntity<CountryResponse> edit(@PathVariable("country-id") long id, @RequestBody @Validated CountryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{country-id}/delete")
    @Operation(summary = "Удаляет страну по id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("country-id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Country has been deleted"));
    }
}
