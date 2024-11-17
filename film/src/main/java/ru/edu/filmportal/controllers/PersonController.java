package ru.edu.filmportal.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.requests.PersonCreateRequest;
import ru.edu.filmportal.models.requests.PersonEditRequest;
import ru.edu.filmportal.models.responses.CareersResponse;
import ru.edu.filmportal.models.responses.MessageResponse;
import ru.edu.filmportal.models.responses.PersonResponse;
import ru.edu.filmportal.services.PersonService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
@Tag(name = "Person Controller")
public class PersonController {
    private final PersonService service;

    @GetMapping("/{person-id}/careers")
    @Operation(summary = "Возвращает роли человека")
    public ResponseEntity<CareersResponse> findCareers(@PathVariable("person-id") long personId) {
        return ResponseEntity.ok(service.findCareers(personId));
    }

    @GetMapping("/all")
    @Operation(summary = "Возвращает всех людей")
    public ResponseEntity<List<PersonResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{person-id}")
    @Operation(summary = "Возвращает человека по id")
    public ResponseEntity<PersonResponse> getById(@PathVariable("person-id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Создаёт нового человека")
    public ResponseEntity<PersonResponse> create(@RequestBody @Validated PersonCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{person-id}/edit")
    @Operation(summary = "Изменяет человека по id")
    public ResponseEntity<PersonResponse> edit(@PathVariable("person-id") long id, @RequestBody PersonEditRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{person-id}/delete")
    @Operation(summary = "Удаляет человка по id")
    public ResponseEntity<MessageResponse> delete(@PathVariable("person-id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Person has been deleted"));
    }
}
