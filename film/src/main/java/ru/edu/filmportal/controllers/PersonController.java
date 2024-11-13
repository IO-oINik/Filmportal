package ru.edu.filmportal.controllers;


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
public class PersonController {
    private final PersonService service;

    @GetMapping("/{person-id}/careers")
    public ResponseEntity<CareersResponse> findCareers(@PathVariable("person-id") long personId) {
        return ResponseEntity.ok(service.findCareers(personId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{person-id}")
    public ResponseEntity<PersonResponse> getById(@PathVariable("person-id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PersonResponse> create(@RequestBody @Validated PersonCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{person-id}/edit")
    public ResponseEntity<PersonResponse> edit(@PathVariable("person-id") long id, @RequestBody PersonEditRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{person-id}/delete")
    public ResponseEntity<MessageResponse> delete(@PathVariable("person-id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Person has been deleted"));
    }
}
