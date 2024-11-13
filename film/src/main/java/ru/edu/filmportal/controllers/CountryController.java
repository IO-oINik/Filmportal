package ru.edu.filmportal.controllers;

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
public class CountryController {
    private final CountryService service;

    @GetMapping("/all")
    public ResponseEntity<List<CountryResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{country-id}")
    public ResponseEntity<CountryResponse> getById(@PathVariable("country-id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CountryResponse> create(@RequestBody @Validated CountryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{country-id}/edit")
    public ResponseEntity<CountryResponse> edit(@PathVariable("country-id") long id, @RequestBody @Validated CountryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{country-id}/delete")
    public ResponseEntity<MessageResponse> delete(@PathVariable("country-id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Country has been deleted"));
    }
}
