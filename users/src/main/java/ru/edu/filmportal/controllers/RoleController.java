package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.filmportal.models.database.Role;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@Tag(name = "Role Controller", description = "Контроллер для работы с ролями")
public class RoleController {

    @GetMapping("/all")
    @Operation(summary = "Получить все роли")
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(Arrays.stream(Role.values()).toList());
    }
}
