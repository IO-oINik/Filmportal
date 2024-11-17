package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.requests.AgeLimitRequest;
import ru.edu.filmportal.models.responses.AgeLimitResponse;
import ru.edu.filmportal.models.responses.MessageResponse;
import ru.edu.filmportal.services.AgeLimitService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/age-limit")
@RequiredArgsConstructor
@Tag(name = "Age Limit Controller")
public class AgeLimitController {
    private final AgeLimitService service;

    @GetMapping("/all")
    @Operation(summary = "Возвращает все возрастные ограничения")
    public ResponseEntity<List<AgeLimitResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{ageLimit-id}")
    @Operation(summary = "Возвращает возрастное ограничение по id")
    public ResponseEntity<AgeLimitResponse> getById(@PathVariable("ageLimit-id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Создаёт новое возрастное ограничение")
    public ResponseEntity<AgeLimitResponse> create(@RequestBody @Validated AgeLimitRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{ageLimit-id}/edit")
    @Operation(summary = "Изменяет возрастное ограничение")
    public ResponseEntity<AgeLimitResponse> edit(@PathVariable("ageLimit-id") long id, @RequestBody @Validated AgeLimitRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{ageLimit-id}/delete")
    @Operation(summary = "Удаляет возрастное ограничение")
    public ResponseEntity<MessageResponse> delete(@PathVariable("ageLimit-id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("AgeLimit has been deleted"));
    }
}
